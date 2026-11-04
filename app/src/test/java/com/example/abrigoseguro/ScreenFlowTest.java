package com.example.abrigoseguro;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowDialog;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 35)
public class ScreenFlowTest {
    AppStore store;
    @Before public void reset() { store = new AppStore(RuntimeEnvironment.getApplication()); store.clear(); }
    private <T extends Activity> ActivityController<T> open(Class<T> type) { return Robolectric.buildActivity(type).setup().visible(); }
    private View find(View root, String text) {
        if (root instanceof TextView && ((TextView)root).getText().toString().equals(text)) return root;
        if (root instanceof ViewGroup) for (int i = 0; i < ((ViewGroup)root).getChildCount(); i++) {
            View match = find(((ViewGroup)root).getChildAt(i), text); if (match != null) return match;
        }
        return null;
    }
    private View view(Activity activity, String text) {
        View found = find(activity.getWindow().getDecorView(), text);
        assertNotNull("Missing UI: " + text, found); return found;
    }
    @Test public void launcherSkipsOnboardingAfterFirstUse() {
        try (ActivityController<MainActivity> c = open(MainActivity.class)) {
            assertEquals(OnbordingActivity.class.getName(), shadowOf(c.get()).getNextStartedActivity().getComponent().getClassName());
        }
        try (ActivityController<OnbordingActivity> c = open(OnbordingActivity.class)) {
            view(c.get(), "Começar →").performClick(); assertTrue(store.flag("onboarded"));
        }
        try (ActivityController<MainActivity> c = open(MainActivity.class)) {
            assertEquals(HomeActivity.class.getName(), shadowOf(c.get()).getNextStartedActivity().getComponent().getClassName());
        }
    }
    @Test public void allMainScreensOpenWithFreshState() {
        Class<?>[] pages = {HomeActivity.class, AbrigosProximosActivity.class, MapaRiscoActivity.class,
            CentralAlertasActivity.class, AlertaActivity.class, AtencaoActivity.class, MinhaRedeApoioActivity.class,
            ModoEmergenciaActivity.class, ModoOfflineActivity.class, PerfilAcessibilidadeActivity.class};
        for (Class<?> page : pages) {
            @SuppressWarnings("unchecked") Class<? extends Activity> activity = (Class<? extends Activity>)page;
            try (ActivityController<? extends Activity> c = open(activity)) {
                assertNotNull(c.get().getWindow().getDecorView().findViewWithTag("fixed_header"));
            }
        }
    }
    @Test public void invalidShelterIsRecoverable() {
        try (ActivityController<DetalhesAbrigoActivity> c = open(DetalhesAbrigoActivity.class)) {
            view(c.get(), "Abrigo não encontrado"); view(c.get(), "Abrir catálogo").performClick();
            assertEquals(AbrigosProximosActivity.class.getName(), shadowOf(c.get()).getNextStartedActivity().getComponent().getClassName());
        }
    }
    @Test public void shelterFavoriteAndRouteUseSelectedId() {
        Intent intent = new Intent(RuntimeEnvironment.getApplication(), DetalhesAbrigoActivity.class).putExtra("shelter_id", "central");
        try (ActivityController<DetalhesAbrigoActivity> c = Robolectric.buildActivity(DetalhesAbrigoActivity.class, intent).setup()) {
            view(c.get(), "Ginásio Central"); view(c.get(), "☆ Salvar nos favoritos").performClick();
            assertTrue(store.favorites().contains("central")); view(c.get(), "Ver trajeto ilustrativo").performClick();
            assertEquals("central", store.text("map_shelter")); assertTrue(store.flag("map_route"));
        }
    }
    @Test public void emptyFavoritesShowsHelpfulState() {
        store.setFlag("favorites_only", true);
        try (ActivityController<AbrigosProximosActivity> c = open(AbrigosProximosActivity.class)) {
            view(c.get(), "Nenhum abrigo encontrado"); view(c.get(), "Limpar filtros").performClick();
            view(c.get(), "4 abrigos encontrados");
        }
    }
    @Test public void alertReadStateAndFilterAreEffective() {
        try (ActivityController<AlertaActivity> c = open(AlertaActivity.class)) { assertTrue(store.flag("read_chuva")); }
        for (DemoData.Alert a : store.alerts()) store.setFlag("read_" + a.id, true);
        store.setFlag("unread_only", true);
        try (ActivityController<CentralAlertasActivity> c = open(CentralAlertasActivity.class)) { view(c.get(), "Nenhum alerta neste filtro"); }
    }
    @Test public void contactDraftSurvivesRecreationAndSaves() {
        try (ActivityController<MinhaRedeApoioActivity> c = open(MinhaRedeApoioActivity.class)) {
            view(c.get(), "Adicionar contato de confiança").performClick();
            ((android.widget.EditText)c.get().getWindow().getDecorView().findViewWithTag("Nome")).setText("Ana");
            ((android.widget.EditText)c.get().getWindow().getDecorView().findViewWithTag("Telefone com DDD")).setText("11999991234");
            c.recreate(); view(c.get(), "Ana"); view(c.get(), "Salvar contato").performClick();
            assertEquals(1, store.contacts().size()); assertFalse(store.flag("editing_contact"));
        }
    }
    @Test public void editorFocusAndCursorSurvivePauseResume() {
        try (ActivityController<MinhaRedeApoioActivity> c = open(MinhaRedeApoioActivity.class)) {
            view(c.get(), "Adicionar contato de confiança").performClick();
            android.widget.EditText name = c.get().getWindow().getDecorView().findViewWithTag("Nome");
            name.setText("Ana Silva"); assertTrue("Editor must accept focus before pausing", name.requestFocus()); name.setSelection(3);
            c.pause().resume(); shadowOf(android.os.Looper.getMainLooper()).idle();
            android.widget.EditText restored = c.get().getWindow().getDecorView().findViewWithTag("Nome");
            assertEquals("Ana Silva", restored.getText().toString()); assertTrue("Focus restored after resume", restored.hasFocus()); assertEquals(3, restored.getSelectionStart());
        }
    }
    @Test @Config(sdk = 24) public void minimumSupportedAndroidOpensHomeAndContacts() {
        try (ActivityController<HomeActivity> c = open(HomeActivity.class)) { view(c.get(), "◈ Abrigo Seguro"); }
        try (ActivityController<MinhaRedeApoioActivity> c = open(MinhaRedeApoioActivity.class)) { view(c.get(), "Adicionar contato de confiança"); }
    }
    @Test public void helpRequiresReviewAndOpensChooserWithoutSending() {
        try (ActivityController<ModoEmergenciaActivity> c = open(ModoEmergenciaActivity.class)) {
            view(c.get(), "Preparar pedido de ajuda de teste").performClick();
            assertNull(shadowOf(c.get()).getNextStartedActivity());
            AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
            shadowOf(android.os.Looper.getMainLooper()).idle();
            Intent chooser = shadowOf(c.get()).getNextStartedActivity(); assertEquals(Intent.ACTION_CHOOSER, chooser.getAction());
            Intent share = chooser.getParcelableExtra(Intent.EXTRA_INTENT); assertEquals(Intent.ACTION_SEND, share.getAction());
            assertTrue(share.getStringExtra(Intent.EXTRA_TEXT).contains("NÃO É EMERGÊNCIA"));
        }
    }
    @Test public void offlineButtonActuallyCreatesPack() {
        try (ActivityController<ModoOfflineActivity> c = open(ModoOfflineActivity.class)) {
            view(c.get(), "Salvar pacote demonstrativo").performClick(); assertTrue(store.hasOffline());
            view(c.get(), "✓ PACOTE SALVO");
        }
    }
    @Test public void largeTextAndContrastStillOpenScreens() {
        store.setFlag("large_text", true); store.setFlag("contrast", true);
        try (ActivityController<PerfilAcessibilidadeActivity> c = open(PerfilAcessibilidadeActivity.class)) {
            view(c.get(), "Texto maior"); assertTrue(c.get().getResources().getConfiguration().fontScale >= 1.2f);
        }
    }
}
