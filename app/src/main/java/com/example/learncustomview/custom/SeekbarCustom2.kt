package com.example.learncustomview.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.math.min


private const val DEFAULT_WIDTH = 300
private const val DEFAULT_HEIGHT = 60
private const val RADIUS_CONTROLLER = 30F
private const val RADIUS_CONTROLLER2 = 60F

class SeekbarCustom2(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var maxProcess = 1000
    private var process = 600
    private var processText = 0
    private var widthProcess = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintViewNumber = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintViewNumberText = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintSeekbar = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintProcess = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintController = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintCircleShadow = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private var centerX = 0f
    private var centerY = 0f
    private var isShowView = false


    init {
        innitPaint()
        innitPaintSeekbar()
        innitPaintProcess()
        innitPainController()
        innitPainViewNumber()
        innitPainViewNumberText()
        innitPainCircleShadow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = measureDimension(DEFAULT_WIDTH, widthMeasureSpec)
        val h = measureDimension(DEFAULT_HEIGHT, heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x < widthProcess + RADIUS_CONTROLLER2 && event.x > widthProcess - RADIUS_CONTROLLER2
                    && event.y < (centerY + centerY / 2) + RADIUS_CONTROLLER2 && event.y > (centerY + centerY / 2) - RADIUS_CONTROLLER2
                ) {
                    isShowView = true
                    invalidate()
                    return true
                }
                return false
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.x > 10f && event.x < width - 10f) {
                    widthProcess = event.x
                    processText = (widthProcess/(width - 10f)*1000).toInt()
                    invalidate()
                    return true
                }
                return false
            }

            MotionEvent.ACTION_UP -> {
                isShowView = false
                invalidate()
                return true
            }

            else -> {}
        }


        return super.onTouchEvent(event)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        centerX = width / 2f
        centerY = height / 2f
        paintSeekbar.strokeWidth = 10f
        paintProcess.strokeWidth = 10f
        if (widthProcess == 0f) {
            widthProcess = (width - 10f) / maxProcess * process
        }
        canvas?.drawLine(
            0f + 10f,
            centerY + centerY / 2,
            width.toFloat() - 10f,
            centerY + centerY / 2,
            paintSeekbar
        )
        canvas?.drawLine(
            0f + 10f,
            centerY + centerY / 2,
            widthProcess,
            centerY + centerY / 2,
            paintProcess
        )
        canvas?.drawCircle(widthProcess, centerY + centerY / 2, RADIUS_CONTROLLER, paintController)
        if (isShowView) {
            drawPath(canvas)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawPath(canvas: Canvas?) {
        path.rewind()
        path.moveTo(widthProcess - 20, (centerY + centerY / 2) - 20)
        path.cubicTo(
            widthProcess - 3,
            (centerY + centerY / 2) - 30,
            widthProcess + 1f,
            (centerY + centerY / 2) - 60,
            widthProcess - 20,
            (centerY + centerY / 2) - 95
        )
        //add circle
        path.arcTo(
            RectF(
                widthProcess - 55f,
                (centerY + centerY / 2) - 200f,
                widthProcess + 55f,
                (centerY + centerY / 2) - 90f
            ), 110f, 320f
        )

        path.cubicTo(
            widthProcess ,
            (centerY + centerY / 2) - 55,
            widthProcess-2 ,
            (centerY + centerY / 2) - 40,
            widthProcess + 15,
            (centerY + centerY / 2) - 20
        )

        path.close()
        canvas?.drawPath(path, paint)
        setTextSizeForWidth(paintViewNumberText,70f, "$processText$")
        canvas?.drawText("$processText$", widthProcess-35,(centerY + centerY/2) - 135f,paintViewNumberText)
        canvas?.drawCircle(widthProcess,(centerY + centerY / 2),60f,paintCircleShadow )
    }


    private fun innitPaint() {
        paint.color = 0xFFFF34988F.toInt()
        paint.strokeWidth = 5f
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    private fun innitPaintSeekbar() {
        paintSeekbar.color = 0xFFFF92B1BF.toInt()
        paintSeekbar.style = Paint.Style.FILL
        paintSeekbar.strokeCap = Paint.Cap.ROUND
        paintSeekbar.isAntiAlias = true
        paintSeekbar.isDither = true
    }

    private fun innitPaintProcess() {
        paintProcess.color = 0xFFFF34988F.toInt()
        paintProcess.style = Paint.Style.STROKE
        paintProcess.strokeCap = Paint.Cap.ROUND
        paintProcess.isAntiAlias = true
        paintProcess.isDither = true
    }

    private fun innitPainController() {
        paintController.color = 0xFFFF34988F.toInt()
        paintProcess.isAntiAlias = true
        paintProcess.isDither = true
    }

    private fun innitPainViewNumber() {
        paintViewNumber.color = 0xFFFF34988F.toInt()
        paintViewNumber.strokeWidth = 82f
        paintViewNumber.strokeCap = Paint.Cap.ROUND
        paintViewNumber.isAntiAlias = true
        paintViewNumber.isDither = true
    }

    private fun innitPainViewNumberText() {
        paintViewNumberText.color = 0xFFFFFFFF.toInt()
        paintViewNumberText.strokeWidth = 20f
        paintViewNumberText.strokeCap = Paint.Cap.ROUND
        paintViewNumberText.isAntiAlias = true
        paintViewNumberText.isDither = true
    }

    private fun innitPainCircleShadow(){
        paintCircleShadow.color = 0xFF4D34988F.toInt()
        paintCircleShadow.style = Paint.Style.FILL
        paintProcess.isAntiAlias = true
        paintProcess.isDither = true
    }


    private fun measureDimension(defaultSize: Int, measureSpec: Int): Int {
        val result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        result = when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> min(defaultSize, specSize)
            else -> defaultSize
        }
        return result
    }

    private fun setTextSizeForWidth(
        paint: Paint, desiredWidth: Float,
        text: String
    ) {
        val testTextSize = 48f
        paint.textSize = testTextSize
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val desiredTextSize: Float = testTextSize * desiredWidth / bounds.width()
        paint.textSize = desiredTextSize
    }
}