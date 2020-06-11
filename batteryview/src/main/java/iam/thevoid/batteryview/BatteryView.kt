@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package iam.thevoid.batteryview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

/**
 * Created by iam on 24.10.17.
 */
class BatteryView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var batteryLevelPaint: Paint? = null
    private var isCharging = false
    private var borderThickness = 0f
    private var batteryLevelCornerRadius = 0f
    private var percent = 0f
    private val rect = RectF(0f, 0f, 0f, 0f)

    @ColorInt
    var color = Color.WHITE
        set(newColor) {
            field = newColor
            setColorFilter(newColor)
            batteryLevelPaint = Paint().apply {
                color = newColor
            }
        }

    init {
        attrs?.also(::obtainAttributes)
        super.setScaleType(ScaleType.FIT_XY)
        super.setImageResource(R.drawable.ic_battery)

        setCharging(isCharging)
        setWillNotDraw(false)
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BatteryView,
                0, 0)
        try {
            isCharging = a.getBoolean(R.styleable.BatteryView_bv_charging, false)
            color = a.getColor(R.styleable.BatteryView_bv_color, Color.WHITE)
            percent = a.getInteger(R.styleable.BatteryView_bv_percent, 0).toFloat()
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        val width = MeasureSpec.getSize(widthSpec)
        val height = MeasureSpec.getSize(heightSpec)
        if (height != 0 && (height <= width || width == 0)) {
            widthSpec = MeasureSpec.makeMeasureSpec((height.toFloat() / 22f * 14f).toInt(), MeasureSpec.EXACTLY)
            borderThickness = height.toFloat() / 22f
        } else if (width != 0 && (width <= height || height == 0)) {
            heightSpec = MeasureSpec.makeMeasureSpec((width.toFloat() / 14f * 22f).toInt(), MeasureSpec.EXACTLY)
            borderThickness = width.toFloat() / 14f
        }
        batteryLevelCornerRadius = borderThickness
        super.onMeasure(widthSpec, heightSpec)
    }

    fun setBatteryLevel(@IntRange(from = 0, to = 100) percent: Int) {
        var tempPercent = percent
        if (percent < 0) {
            tempPercent = 0
        }
        if (percent > 100) {
            tempPercent = 100
        }
        this.percent = tempPercent.toFloat()
        invalidate()
    }

    fun setCharging(charging: Boolean) {
        isCharging = charging
        setImageResource(if (isCharging) R.drawable.ic_charging else R.drawable.ic_battery)
        invalidate()
    }

    fun setColorRes(@ColorRes colorRes: Int) {
        color = ContextCompat.getColor(context, colorRes)
    }


    /**
     * magic numbers for paddings of inside rect
     * 4 is the two battery strokes and two spaces
     * 6 is the stroke, battery top (3 x stroke) andtwo spaces
     * 2 is the one stroke and one space
     */
    override fun onDraw(canvas: Canvas) {
        if (!isCharging) {
            val width = width.toFloat() - 4f * borderThickness
            val height = (height.toFloat() - 6f * borderThickness) * percent / 100f
            val left = 2f * borderThickness
            val top = getHeight().toFloat() - borderThickness * 2f - height
            val right = left + width
            val bottom = top + height
            rect[left, top, right] = bottom
            canvas.drawRoundRect(rect, batteryLevelCornerRadius, batteryLevelCornerRadius, batteryLevelPaint!!)
        }
        super.onDraw(canvas)
    }
}