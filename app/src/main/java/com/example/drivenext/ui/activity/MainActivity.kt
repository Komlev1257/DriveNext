package com.example.drivenext.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.drivenext.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }
}