package com.example.drivenext.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.drivenext.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Твой XML-файл

        // Запускаем таймер на 2 секунды
        Handler(Looper.getMainLooper()).postDelayed({
            navigateNextScreen()
        }, 2000)
    }

    private fun navigateNextScreen() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        var accessToken = sharedPreferences.getString("access_token", null)
        accessToken = "str"
        val nextActivity = if (accessToken.isNullOrEmpty()) {
            GettingStartedActivity::class.java
        } else {
            GettingStartedActivity::class.java
        }

        startActivity(Intent(this, nextActivity))
        finish() // Закрываем экран загрузки
    }
}
