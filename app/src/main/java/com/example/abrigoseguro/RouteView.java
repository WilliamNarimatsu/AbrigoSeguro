package com.example.abrigoseguro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.DashPathEffect;
import android.view.View;

/** Data-driven schematic, deliberately without coordinates or navigation claims. */
public final class RouteView extends View {
    private final DemoData.Shelter destination;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path route = new Path();
    private final DashPathEffect dash;
    public RouteView(Context context, DemoData.Shelter destination) {
        super(context); this.destination = destination;
        float density = getResources().getDisplayMetrics().density;
        dash = new DashPathEffect(new float[]{8 * density, 5 * density}, 0);
        setContentDescription("Trajeto ilustrativo: origem fictícia, ponto de encontro, " + destination.name);
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }
    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float density = getResources().getDisplayMetrics().density, w = getWidth(), h = getHeight();
        float sx = w * .5f, sy = h * .55f, ex = w * destination.x, ey = h * destination.y;
        paint.setColor(0xff244764); paint.setStrokeWidth(density);
        for (int i = 1; i < 6; i++) canvas.drawLine(0, h * i / 6, w, h * i / 6 - 20 * density, paint);
        route.reset(); route.moveTo(sx, sy); route.lineTo(sx, ey); route.lineTo(ex, ey);
        paint.setColor(0xff23d7ff); paint.setStrokeWidth(3 * density); paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(dash); canvas.drawPath(route, paint);
        paint.setPathEffect(null); paint.setStyle(Paint.Style.FILL); paint.setColor(0xff1e88ff);
        canvas.drawCircle(sx, sy, 7 * density, paint); paint.setColor(0xff25d07b); canvas.drawCircle(ex, ey, 8 * density, paint);
        paint.setColor(0xfff5faff); paint.setTextSize(12 * density); paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Origem fictícia", sx, sy + 23 * density, paint);
        canvas.drawText("Destino", ex, ey > h * .6f ? ey - 17 * density : ey + 23 * density, paint);
    }
}
