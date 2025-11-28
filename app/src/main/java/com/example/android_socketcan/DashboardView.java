package com.example.android_socketcan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class DashboardView extends View {
    private Paint paint;
    private float speed = 0; // Speed value that determines the angle of the needle

    public DashboardView(Context context) {
        super(context);
        init(null, 0);
    }

    public DashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Initialize paint object
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Center coordinates
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 20; // Subtract to provide padding

        // Draw the dial
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Draw the needle
        float needleAngle = (float) Math.toRadians((speed / 240) * 240 - 210); // Mapping the speed to -240 to 90 degrees
        int needleLength = radius - 60; // Needle length
        int needleX = centerX + (int) (needleLength * Math.cos(needleAngle));
        int needleY = centerY + (int) (needleLength * Math.sin(needleAngle));

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        canvas.drawLine(centerX, centerY, needleX, needleY, paint);

        // Draw scale numbers
        paint.setColor(Color.WHITE);
        paint.setTextSize(15);
        paint.setTextAlign(Paint.Align.CENTER);

        int scaleLength = radius - 5; // Length of scale lines
        int scaleTextOffset = 15; // Offset for text
        for (int i = -210; i <= 30; i += 10) {
            float angle = (float) Math.toRadians(i);
            int startX = centerX + (int) ((scaleLength - 15) * Math.cos(angle));
            int startY = centerY + (int) ((scaleLength - 15) * Math.sin(angle));
            int endX = centerX + (int) (scaleLength * Math.cos(angle));
            int endY = centerY + (int) (scaleLength * Math.sin(angle));

            int shortstartX = centerX + (int) ((scaleLength - 10) * Math.cos(angle));
            int shortstartY = centerY + (int) ((scaleLength - 10) * Math.sin(angle));

            paint.setStrokeWidth(1);
            canvas.drawLine(shortstartX, shortstartY, endX, endY, paint);
            if (i%20 == 0){
                continue;
            }

            paint.setStrokeWidth(5);
            canvas.drawLine(startX, startY, endX, endY, paint);

            // Draw text
            int textX = centerX + (int) ((scaleLength - 30) * Math.cos(angle));
            int textY = centerY + (int) ((scaleLength - 15) * Math.sin(angle));
            paint.setStrokeWidth(1);
            canvas.drawText(String.valueOf(i + 210), textX, textY + scaleTextOffset, paint);

        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        invalidate(); // Redraw the view with the new speed
    }
}