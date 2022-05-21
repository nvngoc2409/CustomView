package com.example.learncustomview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.learncustomview.R

class SwitchCustom(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        setOnClickListener {
            isSelected = !it.isSelected
        }
        LayoutInflater.from(getContext()).inflate(R.layout.view_custom_switch, this)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        invalidate()
    }
    
}