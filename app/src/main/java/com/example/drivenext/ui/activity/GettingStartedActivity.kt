package com.example.drivenext.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.drivenext.R

class GettingStartedActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getting_started)

        val loginButton: Button = findViewById(R.id.btn_login)
        val registerButton: Button = findViewById(R.id.btn_register)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, SignUpStep1Activity::class.java)
            startActivity(intent)
        }
    }
}
