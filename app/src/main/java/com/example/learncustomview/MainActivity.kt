package com.example.learncustomview

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce


class MainActivity : AppCompatActivity() {
    private val imvBall : ImageView by lazy { findViewById(R.id.imvBall) }
    private val layout : ImageView by lazy { findViewById(R.id.imvBall) }
    private var dX = 0f
    private var dY = 0f

    private val animation by lazy(LazyThreadSafetyMode.NONE) { createSpringAnimation(imvBall, DynamicAnimation.Y) }
    private val animationX by lazy(LazyThreadSafetyMode.NONE) { createSpringAnimation(imvBall, DynamicAnimation.X) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imvBall.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    val newX = event.rawX + dX
                    val newY = event.y
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val height = displayMetrics.heightPixels
                    val width = displayMetrics.widthPixels
                    animation.animateToFinalPosition(height/2f - view.height/2)
                    animationX.animateToFinalPosition(width/2f - view.width/2)
                    playMusicAnimation()
                }

                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    view.animate().x(event.rawX + dX).y(event.rawY + dY).setDuration(0).start()
                }
            }
            return@setOnTouchListener true

        }
    }

    private fun <K> createSpringAnimation(obj: K, property: FloatPropertyCompat<K>): SpringAnimation {
        return SpringAnimation(obj, property).setSpring(SpringForce()).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW

        }
    }

    private inline fun SpringAnimation.onUpdate(crossinline onUpdate: (value: Float) -> Unit): SpringAnimation {
        return this.addUpdateListener { _, value, _ ->
            onUpdate(value)
        }
    }

    private fun playMusicAnimation(){
        val mediaPlayer = MediaPlayer.create(this, R.raw.animation)
        mediaPlayer.setVolume(1f,1f)
        mediaPlayer.start()

    }

    private fun viewSpringAnimation(view: View){
       val animation = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, 100f).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
           spring.stiffness = SpringForce.STIFFNESS_LOW
           spring = SpringForce()
        }
        animation.animateToFinalPosition(500f)
    }



}