package com.example.abrigoseguro;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.android.controller.ActivityController;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 35)
public class NavigationExperienceTest {
    private AppStore store;
    @Before public void setup() { store = new AppStore(RuntimeEnvironment.getApplication()); store.clear(); }
    private View tagged(BaseActivity a, String tag) { return a.getWindow().getDecorView().findViewWithTag(tag); }
    private boolean hasText(View v, String text) {
        if (v instanceof TextView && ((TextView)v).getText().toString().equals(text)) return true;
        if (v instanceof ViewGroup) for (int i=0;i<((ViewGroup)v).getChildCount();i++) if(hasText(((ViewGroup)v).getChildAt(i),text))return true;
        return false;
    }
    @Test public void bottomBarHasFiveAccessibleDestinationsAndNoSidebar() {
        try (ActivityController<HomeActivity> c = Robolectric.buildActivity(HomeActivity.class).setup().visible()) {
            for(int i=0;i<5;i++) { View item=tagged(c.get(),"nav_"+i); assertNotNull(item); assertTrue(item.isClickable()); assertNotNull(item.getContentDescription()); }
            assertNull(tagged(c.get(),"drawer"));
            assertFalse(hasText(c.get().getWindow().getDecorView(),"PROJETO ACADÊMICO • DADOS SIMULADOS"));
        }
    }
    @Test public void activeTabDoesNotReloadAndRepeatedTapNavigatesOnce() {
        try(ActivityController<HomeActivity> c=Robolectric.buildActivity(HomeActivity.class).setup().visible()) {
            tagged(c.get(),"nav_0").performClick(); assertNull(shadowOf(c.get()).getNextStartedActivity());
            tagged(c.get(),"nav_2").performClick(); tagged(c.get(),"nav_2").performClick();
            android.content.Intent next=shadowOf(c.get()).getNextStartedActivity();
            assertEquals(AbrigosProximosActivity.class.getName(),next.getComponent().getClassName());
            assertEquals(0,next.getIntExtra("nav_from",-1)); assertNull(shadowOf(c.get()).getNextStartedActivity());
        }
    }
    @Test public void explicitLightAndDarkPreferencesApplyToPalette() {
        store.setText("theme","light");
        try(ActivityController<HomeActivity> c=Robolectric.buildActivity(HomeActivity.class).setup().visible()) { assertEquals(0xfff3f6fb,c.get().background()); assertEquals(0xff172b43,c.get().TEXT); }
        store.setText("theme","dark");
        try(ActivityController<HomeActivity> c=Robolectric.buildActivity(HomeActivity.class).setup().visible()) { assertEquals(0xff06111f,c.get().background()); assertEquals(0xfff5faff,c.get().TEXT); }
    }
    @Test public void lightHighContrastUsesWhiteBackgroundAndDarkText() {
        store.setText("theme","light"); store.setFlag("contrast",true);
        try(ActivityController<HomeActivity> c=Robolectric.buildActivity(HomeActivity.class).setup().visible()) { assertEquals(android.graphics.Color.WHITE,c.get().background()); assertEquals(android.graphics.Color.BLACK,c.get().muted()); }
    }
    @Test public void originalProfilePhotoAndLocalConnectionAreVisible() {
        try(ActivityController<PerfilAcessibilidadeActivity> c=Robolectric.buildActivity(PerfilAcessibilidadeActivity.class).setup().visible()) {
            ImageView photo=(ImageView)tagged(c.get(),"profile_photo"); assertNotNull(photo); assertNotNull(photo.getDrawable());
            assertTrue(hasText(c.get().getWindow().getDecorView(),"Conectado • perfil local"));
            assertTrue(hasText(c.get().getWindow().getDecorView(),"Diogo"));
            ((android.widget.EditText)tagged(c.get(),"Seu nome")).setText("Diogo Tognolli");
            assertEquals("Diogo Tognolli",store.text("profile_name"));
            assertEquals("Foto de perfil de Diogo Tognolli",photo.getContentDescription());
            assertFalse(hasText(c.get().getWindow().getDecorView(),"Diogo"));
        }
    }
    @Test public void reducedMotionIsPersistedAndDisablesAnimations() {
        store.setFlag("reduce_motion",true);
        try(ActivityController<HomeActivity> c=Robolectric.buildActivity(HomeActivity.class).setup().visible()) {
            assertFalse(c.get().motionEnabled());
        }
    }
}
