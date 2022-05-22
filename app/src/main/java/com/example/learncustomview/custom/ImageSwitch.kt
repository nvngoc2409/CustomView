package com.example.learncustomview.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ImageSwitch(context: Context, attrs: AttributeSet) : AppCompatImageView(context,attrs) {

    init {
        setOnClickListener {
            isSelected = !isSelected
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        invalidate()
    }

}