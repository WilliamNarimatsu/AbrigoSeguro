package com.example.abrigoseguro;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import java.util.function.IntConsumer;

/** Five fixed destinations. Only translation animates; no timers delay navigation. */
public final class BottomBar extends FrameLayout {
    public static final long SLIDE_MS = 180;
    private final BaseActivity host;
    private final LinearLayout row;
    private final View indicator;
    private final int selected;
    private int pendingFrom = -1;
    private int previousWidth;
    public BottomBar(BaseActivity host, int selected, IntConsumer navigate) {
        super(host); this.host = host; this.selected = selected;
        setTag("bottom_navigation"); setElevation(host.dp(8));
        GradientDrawable surface = shape(host.panel(), 26);
        surface.setStroke(host.dp(1), host.store.flag("contrast") ? host.TEXT : ColorUtils.blendARGB(host.TEXT, host.panel(), .88f));
        setBackground(surface);
        indicator = new View(host);
        indicator.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        indicator.setBackground(shape(ColorUtils.blendARGB(host.BLUE, host.panel(), host.isDarkMode() ? .65f : .9f), 18));
        addView(indicator, new FrameLayout.LayoutParams(host.dp(52), host.dp(36)));
        row = new LinearLayout(host); row.setPadding(host.dp(6), 0, host.dp(6), 0);
        String[] names = {"Início", "Mapa", "Abrigos", "SOS", "Perfil"};
        int[] icons = {R.drawable.ic_nav_home, R.drawable.ic_nav_map, R.drawable.ic_nav_shelter, R.drawable.ic_nav_sos, R.drawable.ic_nav_profile};
        for (int i = 0; i < names.length; i++) {
            final int index = i;
            LinearLayout item = new LinearLayout(host); item.setOrientation(LinearLayout.VERTICAL); item.setGravity(Gravity.CENTER);
            item.setPadding(host.dp(2), host.dp(8), host.dp(2), host.dp(9)); item.setMinimumHeight(host.dp(72));
            item.setTag("nav_" + i); item.setFocusable(true); item.setClickable(true); item.setSelected(i == selected);
            item.setContentDescription(names[i] + (i == selected ? ", aba atual" : ""));
            ViewCompat.setStateDescription(item, i == selected ? "Selecionado" : null);
            item.setBackground(new RippleDrawable(ColorStateList.valueOf(ColorUtils.setAlphaComponent(host.CYAN, 35)), null, shape(0xffffffff, 20)));
            ImageView icon = new ImageView(host); icon.setImageResource(icons[i]); icon.setImageTintList(ColorStateList.valueOf(i == selected ? host.CYAN : host.muted()));
            icon.setPadding(host.dp(6), host.dp(6), host.dp(6), host.dp(6)); icon.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
            item.addView(icon, new LinearLayout.LayoutParams(host.dp(36), host.dp(36)));
            TextView label = new TextView(host); label.setText(names[i]); label.setTextSize(12); label.setGravity(Gravity.CENTER);
            label.setTextColor(i == selected ? host.CYAN : host.muted()); label.setTypeface(Typeface.DEFAULT, i == selected ? Typeface.BOLD : Typeface.NORMAL);
            label.setMaxLines(2); label.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
            item.addView(label, new LinearLayout.LayoutParams(-1, -2));
            item.setOnClickListener(v -> navigate.accept(index));
            row.addView(item, new LinearLayout.LayoutParams(0, -2, 1));
        }
        addView(row, new FrameLayout.LayoutParams(-1, -2));
    }
    private GradientDrawable shape(int color, int radius) {
        GradientDrawable shape = new GradientDrawable(); shape.setColor(color); shape.setCornerRadius(host.dp(radius)); return shape;
    }
    private float position(int index) {
        View item = row.getChildAt(index);
        return row.getLeft() + item.getLeft() + (item.getWidth() - indicator.getWidth()) / 2f;
    }
    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = Math.min(host.dp(64), Math.max(host.dp(24), row.getChildAt(selected).getWidth() - host.dp(10)));
        indicator.layout(0, host.dp(8), width, host.dp(44));
        if (previousWidth != getWidth()) { indicator.animate().cancel(); indicator.setTranslationX(position(selected)); previousWidth = getWidth(); }
        if (pendingFrom >= 0) { int from = pendingFrom; pendingFrom = -1; animateFrom(from); }
    }
    public void animateFrom(int from) {
        if (from < 0 || from > 4 || from == selected || !host.motionEnabled()) return;
        if (getWidth() == 0) { pendingFrom = from; return; }
        indicator.animate().cancel(); indicator.setTranslationX(position(from));
        indicator.animate().translationX(position(selected)).setDuration(SLIDE_MS)
            .setInterpolator(new PathInterpolator(.2f, 0f, 0f, 1f)).start();
    }
    @Override protected void onDetachedFromWindow() { indicator.animate().cancel(); super.onDetachedFromWindow(); }
}
