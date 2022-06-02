package com.example.learncustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.learncustomview.animation.springAnimation.SunAnimationSpring
import com.example.learncustomview.custom.SwitchCustom

class MainActivity : AppCompatActivity() {
    private val viewAnimation : SunAnimationSpring by lazy { findViewById(R.id.viewAnimation) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        viewAnimation.also {
//            SpringAnimation(it, DynamicAnimation.TRANSLATION_X, 200f).apply {
//                spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
//                spring.stiffness = SpringForce.STIFFNESS_LOW
//                start()
//            }
//        }
    }
}