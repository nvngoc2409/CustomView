package com.example.learncustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.learncustomview.custom.SwitchCustom

class MainActivity : AppCompatActivity() {
    private val switchCustom : SwitchCustom by lazy { findViewById(R.id.switchCustom) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchCustom.init {
            Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()
        }
    }
}