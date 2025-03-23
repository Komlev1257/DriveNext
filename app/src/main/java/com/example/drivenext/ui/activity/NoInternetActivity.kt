package com.example.drivenext.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.drivenext.R

class NoInternetActivity : AppCompatActivity() {

    private lateinit var btnRetry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRetry = findViewById(R.id.btn_retry)

        // Проверяем интернет-соединение при запуске активности
        if (!isInternetAvailable()) {
            showNoInternetDialog()
        }

        // Кнопка для повторной попытки
        btnRetry.setOnClickListener {
            if (isInternetAvailable()) {
                // Закрыть окно, если интернет появился
                finish()
            } else {
                showNoInternetDialog()
            }
        }
    }

    // Проверка наличия интернета
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    // Показать диалоговое окно, если нет интернета
    private fun showNoInternetDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Нет интернета")
            .setMessage("Проверьте ваше интернет-соединение и повторите попытку.")
            .setCancelable(false)
            .setPositiveButton("Попробовать снова") { dialog, _ ->
                if (isInternetAvailable()) {
                    finish() // Закрыть активность, если интернет доступен
                } else {
                    Toast.makeText(applicationContext, "Интернет не доступен", Toast.LENGTH_SHORT).show()
                }
            }
            .create()

        dialog.show()
    }
}
