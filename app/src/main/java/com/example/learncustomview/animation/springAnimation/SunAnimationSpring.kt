package com.example.learncustomview.animation.springAnimation

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.learncustomview.R

/**
 * Created by Linh on 01,June,2022
 */
class SunAnimationSpring(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs),DynamicAnimation.OnAnimationUpdateListener  {
    init {
        setImageResource(R.drawable.sun)
        initSpringAnimation()
    }

    private fun initSpringAnimation(){
        SpringAnimation(this,DynamicAnimation.TRANSLATION_X, 0f).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }
        SpringAnimation(this,DynamicAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }
    }

    override fun onAnimationUpdate(animation: DynamicAnimation<*>?, value: Float, velocity: Float) {
        SpringAnimation(this, DynamicAnimation.TRANSLATION_X) to
                SpringAnimation(this, DynamicAnimation.TRANSLATION_Y)
    }

}