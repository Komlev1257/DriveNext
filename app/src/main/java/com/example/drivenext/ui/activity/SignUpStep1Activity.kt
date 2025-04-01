package com.example.drivenext.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
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

    private var isPasswordVisible1 = false
    private var isPasswordVisible2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        emailEditText = findViewById(R.id.edit_text1)
        passwordEditText = findViewById(R.id.edit_text2)
        confirmPasswordEditText = findViewById(R.id.edit_text3)
        nextButton = findViewById(R.id.btn_next)
        backButton = findViewById(R.id.back_button)
        passwordVisibilityToggle1 = findViewById(R.id.image_view1)
        passwordVisibilityToggle2 = findViewById(R.id.image_view2)
        termsCheckBox = findViewById(R.id.checkbox1)

        backButton.setOnClickListener { onBackPressed() }

        nextButton.setOnClickListener {
            if (validateFields()) {
                // Передаём email и пароль на второй шаг
                val intent = Intent(this, SignUpStep2Activity::class.java).apply {
                    putExtra("email", emailEditText.text.toString().trim())
                    putExtra("password", passwordEditText.text.toString().trim())
                }
                startActivity(intent)
            }
        }

        passwordVisibilityToggle1.setOnClickListener {
            isPasswordVisible1 = !isPasswordVisible1
            togglePasswordVisibility(passwordEditText, isPasswordVisible1)
        }

        passwordVisibilityToggle2.setOnClickListener {
            isPasswordVisible2 = !isPasswordVisible2
            togglePasswordVisibility(confirmPasswordEditText, isPasswordVisible2)
        }

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

        if (password.length < 6) {
            showError("Пароль должен содержать минимум 6 символов.")
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

    private fun togglePasswordVisibility(editText: EditText, visible: Boolean) {
        if (visible) {
            editText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            editText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        editText.setSelection(editText.text.length)
    }
}
