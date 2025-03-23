package com.example.drivenext.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.drivenext.R
import java.io.InputStream
import java.util.Calendar

class SignUpStep3Activity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnNext: Button
    private lateinit var editTextLicenceNumber: EditText
    private lateinit var editTextIssueDate: EditText
    private lateinit var avatarImageView: ImageView
    private lateinit var btnUploadLicence: Button
    private lateinit var btnUploadPassport: Button
    private lateinit var licenceUploadIndicator: TextView
    private lateinit var passportUploadIndicator: TextView

    private val avatarRequestCode = 1001
    private val licenceRequestCode = 1002
    private val passportRequestCode = 1003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3) // Убедитесь, что XML файл называется activity_sign_up3.xml

        // Инициализация элементов
        btnBack = findViewById(R.id.back_button)
        btnNext = findViewById(R.id.btn_next)
        editTextLicenceNumber = findViewById(R.id.edit_text1)
        editTextIssueDate = findViewById(R.id.edit_text2)
        avatarImageView = findViewById(R.id.btn_avatar)
        btnUploadLicence = findViewById(R.id.btn_upload1)
        btnUploadPassport = findViewById(R.id.btn_upload2)
        licenceUploadIndicator = findViewById(R.id.text_view1) // Индикатор загрузки фото водительского удостоверения
        passportUploadIndicator = findViewById(R.id.text_view1) // Индикатор загрузки фото паспорта

        // Обработка кнопки "Назад"
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Открытие календаря для выбора даты
        editTextIssueDate.setOnClickListener {
            openDatePickerDialog()
        }

        // Обработка кнопки "Next"
        btnNext.setOnClickListener {
            val licenceNumber = editTextLicenceNumber.text.toString().trim()
            val issueDate = editTextIssueDate.text.toString().trim()

            // Проверка валидности введённых данных
            if (licenceNumber.isEmpty() || issueDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Если все данные заполнены, переходим на следующий шаг
                val intent = Intent(this, SplashActivity::class.java).apply {
                    putExtra("LICENCE_NUMBER", licenceNumber)
                    putExtra("ISSUE_DATE", issueDate)
                }
                startActivity(intent)
            }
        }

        // Загрузка фото аватара
        avatarImageView.setOnClickListener {
            openImagePicker(avatarRequestCode)
        }

        // Загрузка фото водительского удостоверения
        btnUploadLicence.setOnClickListener {
            openFilePicker(licenceRequestCode)
        }

        // Загрузка фото паспорта
        btnUploadPassport.setOnClickListener {
            openFilePicker(passportRequestCode)
        }
    }

    private fun openImagePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, requestCode)
    }

    private fun openFilePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                when (requestCode) {
                    avatarRequestCode -> {
                        val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                        avatarImageView.setImageBitmap(bitmap) // Заменяем аватар
                    }
                    licenceRequestCode -> {
                        //licenceUploadIndicator.text = getString(R.string.photo_uploaded) // Индикатор загрузки фото
                    }
                    passportRequestCode -> {
                        //passportUploadIndicator.text = getString(R.string.photo_uploaded) // Индикатор загрузки фото
                    }
                }
            }
        }
    }

    private fun openDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Установим выбранную дату в поле
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                editTextIssueDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}
