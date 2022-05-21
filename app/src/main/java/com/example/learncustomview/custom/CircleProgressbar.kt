package com.example.learncustomview.custom

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.learncustomview.R
import kotlin.math.*

private const val DEFAULT_SIZE = 100
private const val DEFAULT_PROCESS = 60F

class CircleProgressbar(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private var paintMaxProgressbar = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintProgressbar = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintControlProgressbar = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintLine = Paint(Paint.ANTI_ALIAS_FLAG)

    //paint color
    private var maxProgressBarColor: Int = 0xFFFF018786.toInt()
    private var progressBarColor: Int = 0xFFFF03DAC5.toInt()
    private var controlProgressBarColor: Int = 0xFFFFE3884B.toInt()



    private var process: Float = 60f
    private var mAnimator: Animator? = null
    private var startAgle = 270F

    private var controlCircleX: Float = 0f
    private var controlCircleY: Float = 0f

    private var sinAlpha: Double = 0.0
    private var cosAlpha: Double = 0.0
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    init {
        val ta = getContext().obtainStyledAttributes(attributes, R.styleable.CircleProgressbar)
        maxProgressBarColor =
            ta.getColor(R.styleable.CircleProgressbar_setColorMaxProgressbar, Color.GRAY)
        progressBarColor = ta.getColor(R.styleable.CircleProgressbar_setColorProcess, Color.BLUE)
        process = ta.getFloat(R.styleable.CircleProgressbar_setProcess, DEFAULT_PROCESS)
        innitPainMaxProgress()
        innitPainProgress()
        innitPainControlProgress()
        innitPaintLine()
    }


    fun setColorMaxProgressbar(color: Int) {
        paintMaxProgressbar.color = color
        invalidate()
    }

    fun setColorProcess(color: Int) {
        paintProgressbar.color = color
        invalidate()
    }

    private fun measureDimension(defaultSize: Int, measureSpec: Int): Int {
        val result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        result = when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> Math.min(defaultSize, specSize)
            else -> defaultSize
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int =
            measureDimension(DEFAULT_SIZE, widthMeasureSpec)
        val height: Int =
            measureDimension(DEFAULT_SIZE, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val value = super.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x < controlCircleX + 60f && event.x > controlCircleX - 60f && event.y < controlCircleY + 60f && event.y > controlCircleY - 60f) {
                    return true
                }
                return value
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
//                if (x < controlCircleX + 60f && x > controlCircleX - 60f && y < controlCircleY + 60f && y > controlCircleY - 60f) {
                    val tana: Float = abs(y - centerY) / (x - centerX)
                    val gocTan = atan2(y - centerY, x - centerX)
                    val goc: Float = ((gocTan * 180) / PI).toFloat()

                    if (event.x > centerX && event.y < centerY) {
                        process = goc + 90

                    } else if (event.x > centerX && event.y > centerY) {
                        process = goc + 90
                    } else if (event.x < centerX && event.y > centerY) {
                        process = goc + 90
                    } else if (event.x < centerX && event.y < centerY) {
                        process = goc + 450
                    }
//                    controlCircleX = x
//                    controlCircleY = y
                    this.controlCircleX = ((centerX + (sin(PI-process*PI/180)*radius)).toFloat())
                    this.controlCircleY = ((centerY + (cos(PI - process*PI/180)*radius)).toFloat())
                    invalidate()
                    return true
//                }
//                return value
            }
        }
        return value
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = height / 2f
        paintMaxProgressbar.strokeWidth = 30f
        paintProgressbar.strokeWidth = 30f
        radius = min(width, height) / 2f - 15f
        if (controlCircleX == 0f && controlCircleY == 0f) {
            controlCircleX = centerX
            controlCircleY = centerY - radius
        }
        canvas?.apply {
            drawCircle(centerX, centerY, radius, paintMaxProgressbar)
            drawArc(
                RectF(
                    (centerX - radius),
                    (centerY - radius),
                    (centerX + radius),
                    (centerY + radius)
                ), startAgle, process, false, paintProgressbar
            )
            drawLine(centerX, centerY,controlCircleX, controlCircleY, paintLine)
            drawCircle(controlCircleX, controlCircleY, 60f, paintControlProgressbar)
        }
    }

    private fun innitPaintLine(){
        paintLine.color = controlProgressBarColor
        paintLine.strokeWidth = 10f
        paintLine.pathEffect = DashPathEffect(floatArrayOf(20f, 30f, 40f, 50f), 0f)
        paintLine.isAntiAlias = true
        paintLine.isDither = true
    }

    private fun innitPainControlProgress() {
        paintControlProgressbar.color = controlProgressBarColor
        paintMaxProgressbar.isAntiAlias = true
        paintMaxProgressbar.isDither = true
    }

    private fun innitPainMaxProgress() {
        paintMaxProgressbar.color = maxProgressBarColor
        paintMaxProgressbar.style = Paint.Style.STROKE
        paintMaxProgressbar.isAntiAlias = true
        paintMaxProgressbar.isDither = true
    }

    private fun innitPainProgress() {
        paintProgressbar.color = progressBarColor
        paintProgressbar.style = Paint.Style.STROKE
//        paintProgressbar.strokeCap = Paint.Cap.ROUND
        paintProgressbar.isAntiAlias = true
        paintProgressbar.isDither = true
    }

    private fun cancelAnimation() {
        mAnimator?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimation()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        createAnimation()
    }

    private fun updateAnimation(float: Float) {
//        this.startAgle = float
//        this.startAgle = float
        this.controlCircleX = centerX + cos(float)*radius
        this.controlCircleY = centerY + sin(float)*radius
        postInvalidate()

    }

    private fun createAnimation() {
        cancelAnimation()
        val alphaAnim = ValueAnimator.ofFloat(270f, 360f)
        alphaAnim.duration = 360000
        alphaAnim.repeatMode = ValueAnimator.RESTART
        alphaAnim.interpolator = LinearInterpolator()
        alphaAnim.repeatCount = ValueAnimator.INFINITE
        alphaAnim.addUpdateListener { animation ->
            updateAnimation(animation.animatedValue as Float)
        }
        alphaAnim.start()
        mAnimator = alphaAnim
    }



}
