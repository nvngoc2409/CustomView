package com.example.learncustomview.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.learncustomview.R
import com.example.learncustomview.model.TimerCustom
import kotlin.math.min
import kotlin.math.roundToInt


/**
 * Created by Linh Pham on 5/31/2022.
 **/
class TimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val textPaint = Paint()
    private val oval = RectF()
    private val ovalStroke = RectF()
    private val textBounds = Rect()

    var max: Long = 900000
        set(value) {
            field = value
            updateRect()
            invalidate()
        }
    var progress:Long = 60000L
        set(value) {
            field = value
            updateRect()
            invalidate()
        }

    var progressBackgroundColor: Int = Color.parseColor("#44000000")
        set(value) {
            field = value
            invalidate()
        }

    var strokeColor: Int = Color.parseColor("#44000000")
        set(value) {
            field = value
            invalidate()
        }

    var strokeWidth: Int = 20
        set(value) {
            field = value
            updateRect()
            invalidate()
        }

    var textColor: Int = Color.WHITE
        set(value) {
            field = value
            textPaint.color = field
            invalidate()
        }
        get() = textPaint.color

    var textSize: Float = 40f
        set(value) {
            field = value
            textPaint.textSize = field
            invalidate()
        }

    var typeface: Typeface = Typeface.DEFAULT
        set(value) {
            field = value
            textPaint.typeface = field
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL

        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER

        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.TimerView,
                defStyleAttr,
                0
            )
            progressBackgroundColor =
                a.getColor(R.styleable.TimerView_progressBackgroundColor, progressBackgroundColor)
            strokeColor =
                a.getColor(R.styleable.TimerView_progressStrokeColor, strokeColor)
            strokeWidth =
                a.getDimensionPixelOffset(R.styleable.TimerView_strokeWidth, strokeWidth)
            textColor = a.getColor(R.styleable.TimerView_android_textColor, textColor)
            textSize =
                a.getDimensionPixelOffset(R.styleable.TimerView_android_textSize, textSize.toInt())
                    .toFloat()
            val fontFamilyId: Int =
                a.getResourceId(R.styleable.TimerView_textFont, 0)
            if (fontFamilyId > 0) {
                typeface = ResourcesCompat.getFont(context, fontFamilyId) ?: Typeface.DEFAULT
            }
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = strokeColor
        canvas?.drawArc(
            ovalStroke,
            -90f + 360f * progress / max,
            360f - 360f * progress / max,
            true,
            paint
        )

        paint.color = progressBackgroundColor
        canvas?.drawArc(
            oval,
            -90f + 360f * progress / max,
            360f - 360f * progress / max,
            true,
            paint
        )
        val text = getLabel()
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        canvas?.drawText(text, width / 2f, height / 2f - textBounds.centerY() / 2f, textPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateRect()
    }

    private fun updateRect() {
        val size = min(width, height)
        oval.set(
            width / 2f - size / 2f + strokeWidth,
            height / 2f - size / 2f + strokeWidth,
            width / 2f + size / 2f - strokeWidth,
            height / 2f + size / 2f - strokeWidth
        )
        ovalStroke.set(
            width / 2f - size / 2f,
            height / 2f - size / 2f,
            width / 2f + size / 2f,
            height / 2f + size / 2f
        )
    }

    private fun getLabel(): String {
        val second = (progress / 1000f).roundToInt()
        return String.format("%02d:%02d:%02d",(max/1000 - second) / 3600 ,((max/1000 - second) % 3600)/60, (max/1000 - second) % 60)
    }

    private fun getTimerCustom() : TimerCustom {
        val second = (progress / 1000f).roundToInt()

        return TimerCustom(
            hourDozens = (((max/1000 - second) / 3600)/10).toString(),
            hourUnits = (((max/1000 - second) / 3600)%10).toString(),
            minuteDozens =  (((max/1000 - second) % 3600)/60/10).toString(),
            minuteUnits = (((max/1000 - second) % 3600)/60%10).toString(),
            secondDozens = (((max/1000 - second) % 60)/10).toString(),
            secondUnits = (((max/1000 - second) % 60)%10).toString(),
        )
    }

}
