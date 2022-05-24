package com.example.learncustomview.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.learncustomview.R

class ImageSwitch(context: Context, attrs: AttributeSet) : AppCompatImageView(context,attrs) {

    init {
        setImageResource(R.drawable.selector_witch_state)
        setOnClickListener {
            isSelected = !isSelected
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        invalidate()
    }

}