package com.example.drivenext.ui.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drivenext.R
import com.example.drivenext.data.User
import com.example.drivenext.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.edit
import com.bumptech.glide.Glide

class SignUpStep3Activity : AppCompatActivity() {

    private lateinit var editTextLicenseNumber: EditText
    private lateinit var editTextIssueDate: EditText
    private lateinit var btnUploadLicense: ImageButton
    private lateinit var layout_add_lissphoto: ConstraintLayout
    private lateinit var layout_add_passphoto: ConstraintLayout
    private lateinit var btnUploadPassport: ImageButton
    private lateinit var btnNext: Button
    private lateinit var btnBack: ImageButton
    private lateinit var btnAvatar: ImageButton

    private var licensePhotoUri: Uri? = null
    private var passportPhotoUri: Uri? = null
    private var avatarPhotoUri: Uri? = null

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // ViewModel для сохранения пользователя
    private val userViewModel: UserViewModel by viewModels()

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
        layout_add_lissphoto = findViewById(R.id.layout_add_lissphoto)
        layout_add_passphoto = findViewById(R.id.layout_add_passphoto)

        editTextIssueDate.setOnClickListener { showDatePicker() }
        layout_add_lissphoto.setOnClickListener { selectImage(REQUEST_LICENSE_PHOTO) }
        layout_add_passphoto.setOnClickListener { selectImage(REQUEST_PASSPORT_PHOTO) }
        btnAvatar.setOnClickListener { selectImage(REQUEST_AVATAR_PHOTO) }
        btnBack.setOnClickListener { finish() }

        btnNext.setOnClickListener { validateAndRegister() }
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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val uri = data.data ?: return
            when (requestCode) {
                REQUEST_LICENSE_PHOTO -> {
                    licensePhotoUri = uri
                    btnUploadLicense.setImageResource(R.drawable.ic_uploaded) // ✅ заменить иконку
                }
                REQUEST_PASSPORT_PHOTO -> {
                    passportPhotoUri = uri
                    btnUploadPassport.setImageResource(R.drawable.ic_uploaded) // ✅ заменить иконку
                }
                REQUEST_AVATAR_PHOTO -> {
                    avatarPhotoUri = uri
                    Glide.with(this)
                        .load(uri)
                        .circleCrop()
                        .into(btnAvatar)
                }
            }
        }
    }

    private fun validateAndRegister() {
        val licenseNumber = editTextLicenseNumber.text.toString().trim()
        val issueDate = editTextIssueDate.text.toString().trim()

        if (licenseNumber.isEmpty() || issueDate.isEmpty()) {
            showToast("Пожалуйста, заполните все обязательные поля.")
            return
        }

        if (licensePhotoUri == null || passportPhotoUri == null || avatarPhotoUri == null) {
            showToast("Пожалуйста, загрузите все фотографии.")
            return
        }

        // Получаем данные из intent
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val firstName = intent.getStringExtra("firstName") ?: ""
        val lastName = intent.getStringExtra("lastName") ?: ""
        val middleName = intent.getStringExtra("middleName")
        val birthDate = intent.getStringExtra("birthDate") ?: ""
        val gender = intent.getStringExtra("gender") ?: ""

        // Создаём объект User
        val user = User(
            firstName = firstName,
            lastName = lastName,
            middlename = middleName,
            email = email,
            password = password,
            birthDate = birthDate,
            gender = gender,
            licenceNumber = licenseNumber,
            licenceDate = issueDate,
            profilePic = avatarPhotoUri.toString(),
            licencePic = licensePhotoUri.toString(),
            passportPic = passportPhotoUri.toString()
        )

        // Сохраняем пользователя в базу
        userViewModel.insert(user)

        // Сохраняем access token
        saveAuthToken(generateToken(email), email)

        // Переход в основную часть приложения
        startActivity(Intent(this, CongratulationsActivity::class.java))
        finish()
    }

    private fun saveAuthToken(token: String, email: String) {
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit {
            putString("access_token", token)
            putString("user_email", email)
        }
    }

    private fun generateToken(email: String): String {
        return email.hashCode().toString() + System.currentTimeMillis().toString()
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
