package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

/** Launcher routes returning users directly to Home. */
public class MainActivity extends BaseActivity {
    @Override protected void render() {
        navigate(store.flag("onboarded") ? HomeActivity.class : OnbordingActivity.class);
        finish();
    }
}
