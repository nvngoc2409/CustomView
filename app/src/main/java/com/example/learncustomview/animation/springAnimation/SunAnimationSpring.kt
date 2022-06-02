package com.example.learncustomview.animation.springAnimation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.learncustomview.R


/**
 * Created by Linh on 01,June,2022
 */
const val RADIUS_BALL = 60
class SunAnimationSpring(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val icon: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.ball) }
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX : Int = 0
    private var centerY : Int = 0

    init {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val default = super.onTouchEvent(event)
        return when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                if (event.x < centerX + RADIUS_BALL && event.x > centerX - RADIUS_BALL && event.y < centerY + RADIUS_BALL && event.y > centerY - RADIUS_BALL){
                    return true
                }
                return default
            }

            MotionEvent.ACTION_MOVE -> {
                centerX = event.x.toInt()
                centerY = event.y.toInt()
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                centerX = 0
                centerY = 0
                invalidate()
                return true
            }

            else -> default
        }

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (centerX == 0&& centerY == 0){
            centerX = width/2
            centerY = height/2
        }
        canvas?.drawBitmap(icon, null, Rect((centerX-RADIUS_BALL).toInt(),centerY-RADIUS_BALL,centerX+RADIUS_BALL,centerY+RADIUS_BALL), paint)
    }

    private fun initSpringAnimation() {
        SpringAnimation(this, DynamicAnimation.TRANSLATION_X, 0f).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }
        SpringAnimation(this, DynamicAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }
    }

}