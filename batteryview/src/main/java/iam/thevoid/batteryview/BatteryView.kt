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
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

/**
 * Created by iam on 24.10.17.
 */
class BatteryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_BORDER_WIDTH = 0.06f
        const val DEFAULT_INTERNAL_SPACING = 0.5f
    }

    private var batteryLevelPaint: Paint = Paint()
    private var borderThickness = 0f
    private var batteryLevelCornerRadius = 0f
    private val rect = RectF(0f, 0f, 0f, 0f)

    /**
     * Border coefficient. Border is part of icon, so this is just relative dimension to draw other
     * aspects
     */
    private val borderThicknessPercent: Float = DEFAULT_BORDER_WIDTH

    /**
     * Charging percent
     */
    var batteryLevel = 0
        set(value) {
            field = value.limit(0, 100)
            invalidate()
        }

    var isCharging: Boolean = false
        set(value) {
            field = value
            setImageResource(if (isCharging) R.drawable.ic_charging else R.drawable.ic_battery)
            if (isCharging) setColorFilter(infillcolor) else setColorFilter(bordercolor)
            invalidate()
        }

    /**
     * View infill color.
     */
    @ColorInt
    var infillcolor = Color.WHITE
        set(newColor) {
            if (isCharging) setColorFilter(newColor)
            field = newColor
            batteryLevelPaint = Paint().apply {
                color = newColor
                isAntiAlias = true
            }
            invalidate()
        }

    /**
     * View border color
     */
    @ColorInt
    var bordercolor = Color.BLACK
        set(newBorderColor) {
            field = newBorderColor
            if (!isCharging) setColorFilter(newBorderColor)
        }

    /**
     * Spacing between border and internal charge-level rect. Relative as [borderThicknessPercent],
     * but relative to border thickness. Actually is percent of border thickness.
     */
    var internalSpacing: Float = DEFAULT_INTERNAL_SPACING
        set(value) {
            field = value
            invalidate()
        }

    init {
        attrs?.also(::obtainAttributes)
        super.setScaleType(ScaleType.FIT_XY)
        super.setImageResource(R.drawable.ic_battery)

        setWillNotDraw(false)
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BatteryView,
            0, 0
        )
        try {
            isCharging = a.getBoolean(R.styleable.BatteryView_bv_charging, false)
            bordercolor = a.getColor(R.styleable.BatteryView_bv_bordercolor, Color.BLACK)
            infillcolor = a.getColor(R.styleable.BatteryView_bv_infillcolor, Color.WHITE)
            batteryLevel = a.getInteger(R.styleable.BatteryView_bv_percent, 0)
            internalSpacing =
                a.getFloat(R.styleable.BatteryView_bv_internalSpacing, DEFAULT_INTERNAL_SPACING)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        val min = kotlin.math.min(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec))
        widthSpec =
            MeasureSpec.makeMeasureSpec((min.toFloat() / 22f * 17f).toInt(), MeasureSpec.EXACTLY)
        heightSpec =
            MeasureSpec.makeMeasureSpec((min.toFloat() / 17f * 22f).toInt(), MeasureSpec.EXACTLY)
        borderThickness = min.toFloat() * borderThicknessPercent
        batteryLevelCornerRadius = borderThickness
        super.onMeasure(widthSpec, heightSpec)
    }

    fun setInfillColorRes(@ColorRes colorRes: Int) {
        infillcolor = ContextCompat.getColor(context, colorRes)
    }

    fun setBorderColorRes(@ColorRes colorRes: Int) {
        bordercolor = ContextCompat.getColor(context, colorRes)
    }


    /**
     * magic numbers for paddings of inside rect
     * 4 is the two battery strokes and two spaces
     * 6 is the stroke, battery top (3 x stroke) andtwo spaces
     * 2 is the one stroke and one space
     */
    override fun onDraw(canvas: Canvas) {
        if (!isCharging) {
            val rectMarginBorderMultiplier = 1 + internalSpacing
            val width = getWidth().toFloat() - 2 * rectMarginBorderMultiplier * borderThickness
            val height =
                (getHeight().toFloat() - (2 + 2 * rectMarginBorderMultiplier) * borderThickness) * batteryLevel.toFloat() / 100f

            val left = rectMarginBorderMultiplier * borderThickness
            val top = getHeight().toFloat() - borderThickness * rectMarginBorderMultiplier - height

            val right = left + width
            val bottom = top + height
            rect[left, top, right] = bottom
            canvas.drawRoundRect(
                rect,
                batteryLevelCornerRadius,
                batteryLevelCornerRadius,
                batteryLevelPaint
            )
        }
        super.onDraw(canvas)
    }
}

private fun Float.limit(min: Float, max: Float): Float {
    return if (this < min) min else if (this > max) max else this
}

private fun Int.limit(min: Int, max: Int): Int {
    return if (this < min) min else if (this > max) max else this
}