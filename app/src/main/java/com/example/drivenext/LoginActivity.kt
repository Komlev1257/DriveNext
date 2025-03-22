package com.example.drivenext

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getting_started) // Подключаем твой XML

        // Находим кнопки
        val loginButton: Button = findViewById(R.id.btn_login)
        val registerButton: Button = findViewById(R.id.btn_register)

        // Переход на экран авторизации
        loginButton.setOnClickListener {
            //val intent = Intent(this, AuthActivity::class.java)
            //startActivity(intent)
        }

        // Переход на экран регистрации
        registerButton.setOnClickListener {
            //val intent = Intent(this, RegisterActivity::class.java)
            //startActivity(intent)
        }
    }
}
