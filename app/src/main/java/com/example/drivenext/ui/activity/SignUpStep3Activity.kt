package com.example.drivenext.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import com.example.drivenext.R

class SignUpStep3Activity : AppCompatActivity() {

    private lateinit var editTextLicenseNumber: EditText
    private lateinit var editTextIssueDate: EditText
    private lateinit var btnUploadLicense: ImageButton
    private lateinit var btnUploadPassport: ImageButton
    private lateinit var btnNext: Button
    private lateinit var btnBack: ImageButton
    private lateinit var btnAvatar: ImageButton

    private var licensePhotoUri: Uri? = null
    private var passportPhotoUri: Uri? = null
    private var avatarPhotoUri: Uri? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)

        editTextLicenseNumber = findViewById(R.id.edit_text1)
        editTextIssueDate = findViewById(R.id.edit_text2)
        btnUploadLicense = findViewById(R.id.btn_upload1)
        btnUploadPassport = findViewById(R.id.btn_upload2)
        btnNext = findViewById(R.id.btn_next)
        btnBack = findViewById(R.id.back_button)
        btnAvatar = findViewById(R.id.btn_avatar)

        editTextIssueDate.setOnClickListener { showDatePicker() }
        btnUploadLicense.setOnClickListener { selectImage(REQUEST_LICENSE_PHOTO) }
        btnUploadPassport.setOnClickListener { selectImage(REQUEST_PASSPORT_PHOTO) }
        btnAvatar.setOnClickListener { selectImage(REQUEST_AVATAR_PHOTO) }
        btnBack.setOnClickListener { finish() }
        btnNext.setOnClickListener { validateAndProceed() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                editTextIssueDate.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun selectImage(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_LICENSE_PHOTO -> licensePhotoUri = data.data
                REQUEST_PASSPORT_PHOTO -> passportPhotoUri = data.data
                REQUEST_AVATAR_PHOTO -> avatarPhotoUri = data.data
            }
        }
    }

    private fun validateAndProceed() {
        val licenseNumber = editTextLicenseNumber.text.toString().trim()
        val issueDate = editTextIssueDate.text.toString().trim()

        if (licenseNumber.isEmpty() || issueDate.isEmpty()) {
            showToast("Пожалуйста, заполните все обязательные поля.")
            return
        }

        try {
            dateFormat.parse(issueDate)
        } catch (e: Exception) {
            showToast("Введите корректную дату выдачи.")
            return
        }

        if (licensePhotoUri == null || passportPhotoUri == null) {
            showToast("Пожалуйста, загрузите все необходимые фото.")
            return
        }

        // Здесь можно передать данные на следующий экран или сохранить в ViewModel
        showToast("Регистрация завершена!")
        startActivity(Intent(this, GettingStartedActivity::class.java))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_LICENSE_PHOTO = 1
        private const val REQUEST_PASSPORT_PHOTO = 2
        private const val REQUEST_AVATAR_PHOTO = 3
    }
}
