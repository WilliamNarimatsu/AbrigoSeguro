package com.example.abrigoseguro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;
import org.robolectric.android.controller.ActivityController;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk=35, qualifiers="w390dp-h844dp-xhdpi")
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class UiPreviewTest {
    @Test public void renderHomeAndProfileInBothThemes() throws Exception {
        AppStore store = new AppStore(RuntimeEnvironment.getApplication());
        store.clear();
        for (String theme : new String[]{"light", "dark"}) {
            store.setText("theme", theme);
            capture(HomeActivity.class, "home-" + theme);
            capture(PerfilAcessibilidadeActivity.class, "profile-" + theme);
        }
        store.setFlag("large_text",true);
        capture(PerfilAcessibilidadeActivity.class,"profile-large-text");
        store.clear();
    }
    private <T extends BaseActivity> void capture(Class<T> type, String name) throws Exception {
        try (ActivityController<T> controller=Robolectric.buildActivity(type).setup().visible()) {
            View root=controller.get().getWindow().getDecorView();
            root.measure(View.MeasureSpec.makeMeasureSpec(780,View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(1688,View.MeasureSpec.EXACTLY));
            root.layout(0,0,780,1688);
            View bar=root.findViewWithTag("bottom_navigation");
            assertTrue(bar.getHeight()>0);
            for(int i=0;i<5;i++) {
                View item=root.findViewWithTag("nav_"+i);
                assertTrue("Touch target width",item.getWidth()>=controller.get().dp(48));
                assertTrue("Touch target height",item.getHeight()>=controller.get().dp(48));
            }
            Bitmap bitmap=Bitmap.createBitmap(780,1688,Bitmap.Config.ARGB_8888);
            root.draw(new Canvas(bitmap));
            File folder=new File("build/outputs/previews"); assertTrue(folder.isDirectory() || folder.mkdirs());
            try(FileOutputStream output=new FileOutputStream(new File(folder,name+".png"))) { assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG,100,output)); }
            bitmap.recycle();
        }
    }
}
