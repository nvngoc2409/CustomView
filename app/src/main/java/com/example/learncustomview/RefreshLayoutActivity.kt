package com.example.learncustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class RefreshLayoutActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val refreshLayout : SwipeRefreshLayout by lazy { findViewById(R.id.refreshLayout) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_layout)
        refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show()
        Handler().postDelayed({refreshLayout.isRefreshing = false}, 3000)
    }
}