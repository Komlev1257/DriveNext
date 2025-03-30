package com.example.drivenext.ui.activity

import android.os.Bundle
import com.example.drivenext.R
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.edit


class Onboarding1Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding1)

        val skipButton = findViewById<TextView>(R.id.text_view1)
        skipButton.setOnClickListener {
            setPrefs()
            navigateToMainScreen()
        }

        findViewById<View>(R.id.button_1).setOnClickListener {
            val intent = Intent(
                this@Onboarding1Activity,
                Onboarding2Activity::class.java
            )
            startActivity(intent)
        }
    }

    private fun setPrefs() {
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        prefs.edit() { putBoolean("onboarding_shown", true) }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, GettingStartedActivity::class.java) // Укажи здесь свой главный экран
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}