package com.example.abrigoseguro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.AdaptiveIconDrawable;
import java.io.File;
import java.io.FileOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class) @Config(sdk=35)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class LauncherIconTest {
    @Test public void adaptiveLauncherHasBrandAndThemedLayer() throws Exception {
        int iconId=RuntimeEnvironment.getApplication().getApplicationInfo().icon;
        assertEquals(com.example.abrigoseguro.R.mipmap.ic_launcher,iconId);
        Drawable icon=RuntimeEnvironment.getApplication().getDrawable(iconId);
        assertTrue(icon instanceof AdaptiveIconDrawable);
        assertNotNull(((AdaptiveIconDrawable)icon).getMonochrome());
        render(icon,"launcher-adaptive");
        render(RuntimeEnvironment.getApplication().getDrawable(com.example.abrigoseguro.R.mipmap.ic_launcher_round),"launcher-round");
    }
    @Test @Config(sdk=24) @GraphicsMode(GraphicsMode.Mode.LEGACY)
    public void androidSevenResolvesVectorBrandInsteadOfOldAndroidBitmap() {
        Drawable icon=RuntimeEnvironment.getApplication().getDrawable(com.example.abrigoseguro.R.mipmap.ic_launcher);
        assertTrue(icon instanceof android.graphics.drawable.LayerDrawable);
    }
    private void render(Drawable icon,String name) throws Exception {
        Bitmap image=Bitmap.createBitmap(512,512,Bitmap.Config.ARGB_8888);
        icon.setBounds(0,0,512,512); icon.draw(new Canvas(image));
        int blue=0,white=0,green=0;
        for(int y=0;y<512;y++)for(int x=0;x<512;x++) {
            int c=image.getPixel(x,y);
            if(Color.alpha(c)>200) {
                if(Color.blue(c)>Color.red(c)+40 && Color.blue(c)>Color.green(c))blue++;
                if(Color.red(c)>240 && Color.green(c)>240 && Color.blue(c)>240)white++;
                if(Color.green(c)>Color.blue(c)+40 && Color.green(c)>Color.red(c)+40)green++;
            }
        }
        assertTrue("Blue branded background",blue>10000); assertTrue("White house",white>1000); assertTrue("Green protection badge",green>1000);
        File folder=new File("build/outputs/previews"); assertTrue(folder.isDirectory() || folder.mkdirs());
        try(FileOutputStream out=new FileOutputStream(new File(folder,name+".png"))) { assertTrue(image.compress(Bitmap.CompressFormat.PNG,100,out)); }
        image.recycle();
    }
}
