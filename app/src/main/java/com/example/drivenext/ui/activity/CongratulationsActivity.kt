package com.example.drivenext.ui.activity

import com.example.drivenext.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button


class CongratulationsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congratulations)

        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            val intent = Intent(
                this@CongratulationsActivity,
                GettingStartedActivity::class.java
            )
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}