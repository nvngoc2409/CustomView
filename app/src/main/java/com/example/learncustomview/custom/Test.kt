package com.example.learncustomview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Linh on 7/8/2022.
 */
class Test( context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    val paint =  Paint(Paint.ANTI_ALIAS_FLAG)
    init {
        paint.color = 0xFFFF018786.toInt()
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.isDither = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f,height/2f, width.toFloat() ,height/2f,paint)
        canvas?.drawRect(0f + 10,0f + 10, width.toFloat() - 10 ,height.toFloat() - 10,paint)
    }
}