package com.example.drivenext.ui.activity

import android.os.Bundle
import com.example.drivenext.R
import android.content.Intent
import android.view.View
import android.widget.TextView


class Onboarding3Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding3)

        val skipButton = findViewById<TextView>(R.id.text_view1)
        skipButton.setOnClickListener {
            navigateToMainScreen()
        }

        findViewById<View>(R.id.button_1).setOnClickListener {
            val intent = Intent(
                this@Onboarding3Activity,
                GettingStartedActivity::class.java
            )
            startActivity(intent)
        }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, GettingStartedActivity::class.java) // Укажи здесь свой главный экран
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}