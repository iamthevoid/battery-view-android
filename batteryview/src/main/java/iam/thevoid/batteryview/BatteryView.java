package iam.thevoid.batteryview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.util.AttributeSet;

/**
 * Created by iam on 24.10.17.
 */

public class BatteryView extends android.support.v7.widget.AppCompatImageView {

    private Paint batteryLevelPaint;

    private boolean isCharging = false;

    private int mColor = Color.WHITE;

    private float borderThickness;

    private float batteryLevelCornerRadius;

    private float percent = 0;

    private RectF rect = new RectF(0, 0, 0, 0);

    public BatteryView(Context context) {
        super(context);
        init();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(attrs);
        init();
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(attrs);
        init();
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BatteryView,
                0, 0);

        try {
            isCharging = a.getBoolean(R.styleable.BatteryView_bv_charging, false);
            mColor = a.getColor(R.styleable.BatteryView_bv_color, Color.WHITE);
            percent = a.getInteger(R.styleable.BatteryView_bv_percent, 0);
        } finally {
            a.recycle();
        }
    }

    void init() {
        super.setScaleType(ScaleType.FIT_XY);
        super.setImageResource(R.drawable.ic_battery);
        setColorFilter(mColor);
        batteryLevelPaint = new Paint();
        batteryLevelPaint.setColor(mColor);
        setCharging(isCharging);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (height != 0 && (height <= width || width == 0)) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) ((float) height / 22f * 14f), MeasureSpec.EXACTLY);
            borderThickness = (int) ((float) height / 22f);
        } else if (width != 0 && (width <= height || height == 0)) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) ((float) width / 14f * 22f), MeasureSpec.EXACTLY);
            borderThickness = (int) ((float) width / 14f);
        }

        batteryLevelCornerRadius = borderThickness;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setBatteryLevel(@IntRange(from = 0, to = 100) int percent) {

        int tempPercent = percent;

        if (percent < 0) {
            tempPercent = 0;
        }

        if (percent > 100) {
            tempPercent = 100;
        }

        this.percent = tempPercent;
        invalidate();
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
        setImageResource(isCharging ? R.drawable.ic_charging : R.drawable.ic_battery);
        invalidate();
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    /**
     * magic numbers for paddings of inside rect
     * 4 is the two battery strokes and two spaces
     * 6 is the stroke, battery top (3 x stroke) andtwo spaces
     * 2 is the one stroke and one space
     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (!isCharging) {
            float width = (float) getWidth() - 4f * borderThickness;
            float height = ((float) getHeight() - 6f * borderThickness) * percent / 100f;

            float left = 2f * borderThickness;
            float top = (float) getHeight() - borderThickness * 2f - height;
            float right = left + width;
            float bottom = top + height;
            rect.set(left, top, right, bottom);
            canvas.drawRoundRect(rect, batteryLevelCornerRadius, batteryLevelCornerRadius, batteryLevelPaint);
        }

        super.onDraw(canvas);
    }
}
