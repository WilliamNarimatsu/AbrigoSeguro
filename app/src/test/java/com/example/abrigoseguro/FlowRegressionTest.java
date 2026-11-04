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

@RunWith(RobolectricTestRunner.class) @Config(sdk=35)
public class FlowRegressionTest {
    AppStore store;
    @Before public void reset() { store=new AppStore(RuntimeEnvironment.getApplication()); store.clear(); }
    private View find(View root,String text) {
        if(root instanceof TextView && text.equals(((TextView)root).getText().toString())) return root;
        if(root instanceof ViewGroup) for(int i=0;i<((ViewGroup)root).getChildCount();i++) {
            View found=find(((ViewGroup)root).getChildAt(i),text); if(found!=null)return found;
        }
        return null;
    }
    private View view(Activity activity,String text) { View result=find(activity.getWindow().getDecorView(),text); assertNotNull(text,result); return result; }
    @Test public void alertCenterBelongsToHomeDestination() {
        try(ActivityController<CentralAlertasActivity> c=Robolectric.buildActivity(CentralAlertasActivity.class).setup().visible()) {
            assertTrue(c.get().getWindow().getDecorView().findViewWithTag("nav_0").isSelected());
        }
    }
    @Test public void invalidAlertCanReturnToCenter() {
        Intent intent=new Intent(RuntimeEnvironment.getApplication(),AlertaActivity.class).putExtra("alert_id","missing");
        try(ActivityController<AlertaActivity> c=Robolectric.buildActivity(AlertaActivity.class,intent).setup()) {
            view(c.get(),"Abrir central de alertas").performClick();
            assertEquals(CentralAlertasActivity.class.getName(),shadowOf(c.get()).getNextStartedActivity().getComponent().getClassName());
        }
    }
    @Test public void favoritesRefreshAfterReturningFromDetails() {
        store.setFlag("favorites_only",true);
        try(ActivityController<AbrigosProximosActivity> c=Robolectric.buildActivity(AbrigosProximosActivity.class).setup()) {
            view(c.get(),"Nenhum abrigo encontrado"); c.pause();
            store.toggleFavorite("central"); c.resume(); view(c.get(),"★ Ginásio Central");
        }
    }
    @Test public void offlineRemovalRequiresConfirmationAndKeepsPersonalData() throws Exception {
        store.saveOffline(); store.toggleFavorite("central"); store.saveContact(null,"Ana","11999991234");
        try(ActivityController<ModoOfflineActivity> c=Robolectric.buildActivity(ModoOfflineActivity.class).setup()) {
            view(c.get(),"Remover pacote salvo").performClick(); assertTrue(store.hasOffline());
            ((AlertDialog)ShadowDialog.getLatestDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).performClick(); assertTrue(store.hasOffline());
            view(c.get(),"Remover pacote salvo").performClick();
            ((AlertDialog)ShadowDialog.getLatestDialog()).getButton(AlertDialog.BUTTON_POSITIVE).performClick();
            shadowOf(android.os.Looper.getMainLooper()).idle();
            assertFalse(store.hasOffline()); assertTrue(store.favorites().contains("central")); assertEquals(1,store.contacts().size());
            view(c.get(),"CONTEÚDO LOCAL");
        }
    }
    @Test public void themeButtonsPersistAndFollowSelection() {
        try(ActivityController<PerfilAcessibilidadeActivity> c=Robolectric.buildActivity(PerfilAcessibilidadeActivity.class).setup()) {
            view(c.get(),"Escuro").performClick(); assertEquals("dark",store.text("theme"));
            c.recreate(); assertTrue(c.get().isDarkMode());
            assertTrue(((com.google.android.material.button.MaterialButton)view(c.get(),"Escuro")).isChecked());
            view(c.get(),"Claro").performClick(); c.recreate(); assertFalse(c.get().isDarkMode());
            view(c.get(),"Sistema").performClick(); assertEquals("",store.text("theme"));
        }
    }
    @Test public void selectedShelterTabReturnsFromDetailsToCatalog() {
        Intent intent=new Intent(RuntimeEnvironment.getApplication(),DetalhesAbrigoActivity.class).putExtra("shelter_id","central");
        try(ActivityController<DetalhesAbrigoActivity> c=Robolectric.buildActivity(DetalhesAbrigoActivity.class,intent).setup()) {
            c.get().getWindow().getDecorView().findViewWithTag("nav_2").performClick();
            assertEquals(AbrigosProximosActivity.class.getName(),shadowOf(c.get()).getNextStartedActivity().getComponent().getClassName());
        }
    }
}
