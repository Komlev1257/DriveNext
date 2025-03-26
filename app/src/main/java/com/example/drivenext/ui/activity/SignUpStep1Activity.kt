package com.example.drivenext.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.drivenext.R

class SignUpStep1Activity : BaseActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var nextButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var passwordVisibilityToggle1: ImageView
    private lateinit var passwordVisibilityToggle2: ImageView
    private lateinit var termsCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        // Инициализация элементов
        emailEditText = findViewById(R.id.edit_text1)
        passwordEditText = findViewById(R.id.edit_text2)
        confirmPasswordEditText = findViewById(R.id.edit_text3)
        nextButton = findViewById(R.id.btn_next)
        backButton = findViewById(R.id.back_button)
        passwordVisibilityToggle1 = findViewById(R.id.image_view1)
        passwordVisibilityToggle2 = findViewById(R.id.image_view2)
        termsCheckBox = findViewById(R.id.checkbox1)

        // Устанавливаем обработчики для кнопки "Назад"
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Логика для кнопки "Далее"
        nextButton.setOnClickListener {
            if (validateFields()) {
                // Перейти на следующий экран регистрации
                val intent = Intent(this, SignUpStep2Activity::class.java)
                startActivity(intent)
            }
        }

        // Логика показа/скрытия пароля
        passwordVisibilityToggle1.setOnClickListener {
            togglePasswordVisibility(passwordEditText)
        }

        passwordVisibilityToggle2.setOnClickListener {
            togglePasswordVisibility(confirmPasswordEditText)
        }

        // Отслеживание изменений в полях ввода
        emailEditText.addTextChangedListener { enableNextButton() }
        passwordEditText.addTextChangedListener { enableNextButton() }
        confirmPasswordEditText.addTextChangedListener { enableNextButton() }
        termsCheckBox.setOnCheckedChangeListener { _, _ -> enableNextButton() }
    }

    private fun enableNextButton() {
        val isEmailValid = emailEditText.text.isNotEmpty() && emailEditText.text.contains("@")
        val isPasswordValid = passwordEditText.text.isNotEmpty() && confirmPasswordEditText.text.isNotEmpty()
        val arePasswordsEqual = passwordEditText.text.toString() == confirmPasswordEditText.text.toString()
        val isTermsChecked = termsCheckBox.isChecked

        nextButton.isEnabled = isEmailValid && isPasswordValid && arePasswordsEqual && isTermsChecked
    }

    private fun validateFields(): Boolean {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (email.isEmpty() || !email.contains("@")) {
            showError("Введите корректный адрес электронной почты.")
            return false
        }

        if (password != confirmPassword) {
            showError("Пароли не совпадают.")
            return false
        }

        if (!termsCheckBox.isChecked) {
            showError("Необходимо согласиться с условиями обслуживания и политикой конфиденциальности.")
            return false
        }

        return true
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun togglePasswordVisibility(editText: EditText) {
        if (editText.transformationMethod == null) {
            editText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
        } else {
            editText.transformationMethod = null
        }
    }
}

