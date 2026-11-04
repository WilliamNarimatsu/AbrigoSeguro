package com.example.abrigoseguro;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Build;
import android.animation.ValueAnimator;
import android.provider.Settings;
import android.view.animation.PathInterpolator;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import java.util.function.Consumer;

/** Shared, scrollable native UI. All actions have visible labels and touch targets. */
public abstract class BaseActivity extends AppCompatActivity {
    protected AppStore store;
    protected LinearLayout body;
    protected int BG = 0xff06111f, PANEL = 0xff0e2035, TEXT = 0xfff5faff,
            MUTED = 0xffa5bad0, GREEN = 0xff25d07b, CYAN = 0xff23d7ff, RED = 0xffff8f9a,
            BLUE = 0xff126adb, AMBER = 0xffffb020;
    private boolean large, contrast;
    private ScrollView pageScroll;
    private int resumeScroll, resumeSelection;
    private String resumeField;
    private long pausedRevision;
    private String appearance;
    private boolean darkMode, navigating, navigationEntryPending;
    private BottomBar bottomBar;
    @Override protected void attachBaseContext(Context base) {
        Configuration config = new Configuration(base.getResources().getConfiguration());
        if (new AppStore(base).flag("large_text")) config.fontScale *= 1.2f;
        super.attachBaseContext(base.createConfigurationContext(config));
    }
    @Override protected void onCreate(Bundle state) {
        store = new AppStore(this);
        appearance = store.text("theme");
        getDelegate().setLocalNightMode("light".equals(appearance) ? AppCompatDelegate.MODE_NIGHT_NO
            : "dark".equals(appearance) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        super.onCreate(state);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        darkMode = "dark".equals(appearance) || (!"light".equals(appearance)
            && (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES);
        if (!darkMode) {
            BG = 0xfff3f6fb; PANEL = Color.WHITE; TEXT = 0xff172b43; MUTED = 0xff50637a;
            GREEN = 0xff087849; CYAN = 0xff09659b; RED = 0xffb4233b; AMBER = 0xff895800;
        }
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(!darkMode);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightNavigationBars(!darkMode);
        navigationEntryPending = state == null && getIntent().hasExtra("nav_from");
        large = store.flag("large_text"); contrast = store.flag("contrast");
        if (state != null) {
            resumeScroll = state.getInt("page_scroll"); resumeField = state.getString("focused_field");
            resumeSelection = state.getInt("field_selection");
        }
    }
    @Override protected void onResume() {
        super.onResume();
        navigating = false;
        if (large != store.flag("large_text") || contrast != store.flag("contrast") || !appearance.equals(store.text("theme"))) { recreate(); return; }
        // External activities do not change our data: retain the actual view tree,
        // scroll position, keyboard and TalkBack focus instead of rebuilding it.
        if (body != null && pausedRevision == store.revision()) { animateNavigationEntry(); return; }
        render();
        if (pageScroll != null) {
            if (resumeField != null && body != null) {
                View field = body.findViewWithTag(resumeField);
                if (field instanceof AppCompatEditText) {
                    AppCompatEditText editor = (AppCompatEditText) field;
                    editor.requestFocus(); editor.setSelection(Math.min(Math.max(0, resumeSelection), editor.length()));
                }
            }
            pageScroll.post(() -> pageScroll.scrollTo(0, resumeScroll));
        }
        animateNavigationEntry();
    }
    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent); setIntent(intent); navigationEntryPending = intent.hasExtra("nav_from");
    }
    public boolean isDarkMode() { return darkMode; }
    protected boolean motionEnabled() {
        if (store.flag("reduce_motion")) return false;
        return Build.VERSION.SDK_INT >= 26 ? ValueAnimator.areAnimatorsEnabled()
            : Settings.Global.getFloat(getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f;
    }
    private void animateNavigationEntry() {
        if (!navigationEntryPending) return;
        navigationEntryPending = false;
        int from = getIntent().getIntExtra("nav_from", navigationIndex(getClass()));
        if (bottomBar != null) bottomBar.animateFrom(from);
        if (body != null && motionEnabled() && from != navigationIndex(getClass())) {
            body.animate().cancel(); body.setAlpha(.65f); body.setTranslationX(dp(12) * (navigationIndex(getClass()) > from ? 1 : -1));
            body.animate().alpha(1f).translationX(0).setDuration(BottomBar.SLIDE_MS).setInterpolator(new PathInterpolator(.2f, 0f, 0f, 1f)).start();
        }
    }
    private void rememberPosition() {
        if (pageScroll != null) resumeScroll = pageScroll.getScrollY();
        View focused = getCurrentFocus(); resumeField = null;
        if (focused instanceof AppCompatEditText && focused.getTag() instanceof String) {
            resumeField = (String) focused.getTag(); resumeSelection = ((AppCompatEditText) focused).getSelectionStart();
        }
    }
    @Override protected void onPause() { rememberPosition(); pausedRevision = store.revision(); super.onPause(); }
    @Override protected void onSaveInstanceState(Bundle state) {
        rememberPosition(); state.putInt("page_scroll", resumeScroll); state.putString("focused_field", resumeField);
        state.putInt("field_selection", resumeSelection); super.onSaveInstanceState(state);
    }
    protected abstract void render();
    protected int background() { return store.flag("contrast") ? (darkMode ? Color.BLACK : Color.WHITE) : BG; }
    protected int panel() { return store.flag("contrast") ? (darkMode ? 0xff111111 : Color.WHITE) : PANEL; }
    protected int muted() { return store.flag("contrast") ? (darkMode ? Color.WHITE : Color.BLACK) : MUTED; }
    protected int dp(float size) { return Math.round(size * getResources().getDisplayMetrics().density); }
    protected LinearLayout column() {
        LinearLayout result = new LinearLayout(this); result.setOrientation(LinearLayout.VERTICAL); return result;
    }
    protected void screen(String title, String subtitle, boolean navigation) {
        LinearLayout root = column(); root.setBackgroundColor(background());
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {
            Insets safe = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.ime());
            view.setPadding(safe.left, safe.top, safe.right, safe.bottom); return insets;
        });
        if (!title.isEmpty()) {
            LinearLayout header = new LinearLayout(this); header.setGravity(Gravity.CENTER_VERTICAL); header.setPadding(dp(16), dp(8), dp(16), dp(8));
            header.setMinimumHeight(dp(64)); header.setTag("fixed_header");
            if (!(this instanceof HomeActivity) && !(this instanceof MainActivity) && !(this instanceof OnbordingActivity)) {
                ImageView back = new ImageView(this); back.setImageResource(R.drawable.ic_back); back.setImageTintList(ColorStateList.valueOf(TEXT));
                back.setPadding(dp(12),dp(12),dp(12),dp(12)); back.setContentDescription("Voltar"); back.setFocusable(true);
                back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
                header.addView(back, new LinearLayout.LayoutParams(dp(48), dp(48)));
            }
            TextView heading = new TextView(this); heading.setText(title); heading.setTextSize(20);
            heading.setTextColor(this instanceof HomeActivity ? GREEN : TEXT); heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            ViewCompat.setAccessibilityHeading(heading, true); header.addView(heading, new LinearLayout.LayoutParams(0, -2, 1));
            if (navigation && !(this instanceof PerfilAcessibilidadeActivity)) {
                ImageView avatar = profilePhoto(48); avatar.setContentDescription("Abrir meu perfil"); avatar.setFocusable(true);
                avatar.setOnClickListener(v -> navigate(PerfilAcessibilidadeActivity.class)); header.addView(avatar);
            }
            root.addView(header);
        }
        ScrollView scroll = new ScrollView(this); scroll.setFillViewport(true); pageScroll = scroll; scroll.setClipToPadding(false);
        body = column(); body.setPadding(dp(20), dp(4), dp(20), dp(24));
        scroll.addView(body); root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
        if (!subtitle.isEmpty()) label(body, subtitle, 14, muted(), false);
        if (navigation) addNavigation(root);
        setContentView(root); ViewCompat.requestApplyInsets(root);
    }
    private void addNavigation(LinearLayout root) {
        Class<?>[] pages = {HomeActivity.class, MapaRiscoActivity.class, AbrigosProximosActivity.class, ModoEmergenciaActivity.class, PerfilAcessibilidadeActivity.class};
        bottomBar = new BottomBar(this, navigationIndex(getClass()), index -> navigate(pages[index]));
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(-1, -2);
        layout.setMargins(dp(14), dp(8), dp(14), dp(10)); root.addView(bottomBar, layout);
    }
    static int navigationIndex(Class<?> page) {
        if (page == HomeActivity.class || page == AtencaoActivity.class || page == AlertaActivity.class || page == CentralAlertasActivity.class) return 0;
        if (page == MapaRiscoActivity.class) return 1;
        if (page == AbrigosProximosActivity.class || page == DetalhesAbrigoActivity.class) return 2;
        if (page == ModoEmergenciaActivity.class) return 3;
        return 4;
    }
    protected void navigate(Class<?> page) {
        if (getClass() == page || navigating) return;
        navigating = true;
        startActivity(new Intent(this, page).putExtra("nav_from", navigationIndex(getClass()))
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
    protected ImageView profilePhoto(int size) {
        ImageView photo = new ImageView(this); photo.setImageResource(R.drawable.toga); photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GradientDrawable outline = new GradientDrawable(); outline.setShape(GradientDrawable.OVAL); outline.setColor(panel()); photo.setBackground(outline);
        photo.setClipToOutline(true); photo.setContentDescription("Foto de perfil de " + profileName());
        photo.setLayoutParams(new LinearLayout.LayoutParams(dp(size), dp(size))); return photo;
    }
    protected String profileName() { return store.text("profile_name").trim().isEmpty() ? "Diogo" : store.text("profile_name").trim(); }
    protected void shelter(String id) { startActivity(new Intent(this, DetalhesAbrigoActivity.class).putExtra("shelter_id", id)); }
    protected LinearLayout card(String title, String description) {
        LinearLayout box = column(); box.setPadding(dp(18), dp(16), dp(18), dp(16));
        GradientDrawable bg = new GradientDrawable(); bg.setColor(panel()); bg.setCornerRadius(dp(18));
        bg.setStroke(dp(1), store.flag("contrast") ? TEXT : (darkMode ? 0xff29425b : 0xffdbe4ef)); box.setBackground(bg);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(-1, -2); layout.topMargin = dp(14);
        body.addView(box, layout);
        if (!title.isEmpty()) { TextView text = label(box, title, 17, TEXT, true); ViewCompat.setAccessibilityHeading(text, true); }
        if (!description.isEmpty()) label(box, description, 14, muted(), false);
        return box;
    }
    protected void tintCard(LinearLayout box, int fill, int border) {
        GradientDrawable bg = new GradientDrawable(); bg.setColor(store.flag("contrast") ? background() : (darkMode ? fill : ColorUtils.blendARGB(fill,Color.WHITE,.92f)));
        bg.setCornerRadius(dp(20)); bg.setStroke(dp(1), store.flag("contrast") ? TEXT : border); box.setBackground(bg);
    }
    protected void illustration(LinearLayout parent, int resource, String description, int height) {
        ImageView image = new ImageView(this); image.setImageResource(resource);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER); image.setAdjustViewBounds(true);
        image.setContentDescription(description); parent.addView(image, new LinearLayout.LayoutParams(-1, dp(height)));
    }
    protected TextView label(LinearLayout parent, String value, int size, int color, boolean bold) {
        TextView view = new TextView(this); view.setText(value); view.setTextSize(size); view.setTextColor(color);
        view.setPadding(0, dp(5), 0, dp(7)); view.setLineSpacing(dp(3), 1);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        parent.addView(view, new LinearLayout.LayoutParams(-1, -2)); return view;
    }
    protected MaterialButton button(LinearLayout parent, String title, boolean primary, Runnable action) {
        MaterialButton button = new MaterialButton(this); button.setText(title); button.setAllCaps(false);
        button.setTextSize(15); button.setMinHeight(dp(52)); button.setCornerRadius(dp(12));
        button.setTextColor(primary ? Color.WHITE : CYAN);
        button.setBackgroundTintList(ColorStateList.valueOf(primary ? BLUE : panel()));
        button.setStrokeColor(ColorStateList.valueOf(primary ? BLUE : (darkMode ? 0xff46647f : 0xffd0dce9))); button.setStrokeWidth(dp(1));
        button.setPadding(dp(14), dp(10), dp(14), dp(10)); button.setOnClickListener(v -> action.run());
        parent.addView(button, new LinearLayout.LayoutParams(-1, -2)); return button;
    }
    protected AppCompatCheckBox check(LinearLayout parent, String title, boolean checked, Consumer<Boolean> onChange) {
        AppCompatCheckBox box = new AppCompatCheckBox(this); box.setText(title); box.setTextSize(16);
        box.setTextColor(TEXT); box.setMinHeight(dp(48)); box.setChecked(checked);
        box.setButtonTintList(ColorStateList.valueOf(GREEN));
        box.setOnCheckedChangeListener((v, value) -> onChange.accept(value)); parent.addView(box); return box;
    }
    protected AppCompatEditText input(LinearLayout parent, String title, String value, int type, Consumer<String> change) {
        TextView caption = label(parent, title, 14, muted(), true);
        AppCompatEditText field = new AppCompatEditText(this); field.setId(View.generateViewId()); caption.setLabelFor(field.getId());
        field.setTag(title);
        field.setTextColor(TEXT); field.setHintTextColor(muted()); field.setTextSize(17); field.setInputType(type);
        field.setMinHeight(dp(52)); field.setText(value); field.setSelectAllOnFocus(false);
        field.setPadding(dp(8), dp(8), dp(8), dp(8)); parent.addView(field, new LinearLayout.LayoutParams(-1, -2));
        field.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { change.accept(s.toString()); }
            public void afterTextChanged(Editable e) { }
        }); return field;
    }
    protected void notice(String message) { Toast.makeText(this, message, Toast.LENGTH_LONG).show(); }
    protected void confirm(String title, String message, Runnable action) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setNegativeButton("Cancelar", null)
            .setPositiveButton("Confirmar", (d, w) -> action.run()).show();
    }
    protected void external(Intent intent) {
        try { startActivity(intent); }
        catch (ActivityNotFoundException | SecurityException e) { notice("Nenhum aplicativo disponível para esta ação neste dispositivo."); }
    }
    protected void share(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, message);
        external(Intent.createChooser(intent, "Compartilhar pelo aplicativo"));
    }
}
