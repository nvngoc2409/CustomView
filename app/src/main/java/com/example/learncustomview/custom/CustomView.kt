package com.example.learncustomview.custom

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.example.learncustomview.R
const val SQUARE_SIZE = 100

class CustomView constructor(context: Context, attributes: AttributeSet) :
    View(context, attributes) {
    private var rect = Rect()
    private var paint = Paint(ANTI_ALIAS_FLAG)
    private var mShowText : Boolean = false
    private var mTextPos : Int = 0
    private var textColor : Int = 0
    private var textHeight : Float = 0f

    init {
        context.theme.obtainStyledAttributes(attributes, R.styleable.CustomView, 0, 0).apply {
            try {
                mShowText = getBoolean(R.styleable.CustomView_showText, false)
                mTextPos = getInteger(R.styleable.CustomView_showText, 0)
            }finally {
                recycle()
            }
        }

    }

    fun isShowText(): Boolean {
        return this.mShowText
    }

    fun setShowText(boolean: Boolean){
        mShowText = boolean
        invalidate()
        requestLayout()
    }

    fun isTextPos(): Int{
        return this.mTextPos
    }

    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = textColor
        if (textHeight == 0f) {
            textHeight = textSize
        } else {
            textSize = textHeight
        }
    }

    private val piePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = textHeight
    }

    private val shadowPaint = Paint(0).apply {
        color = 0x101010
        maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
    }

    override fun onDraw(canvas: Canvas?) {
        rect.left = 10
        rect.top = 10
        rect.right = rect.left + width - 10
        rect.bottom = rect.top + height - 10

        paint.setColor(Color.GRAY)
        canvas?.drawRect(rect, paint)
    }

}