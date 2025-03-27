package com.example.drivenext.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.drivenext.R
import com.example.drivenext.viewmodel.UserViewModel

class LoginActivity : BaseActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var googleLoginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var passwordVisibilityToggle: ImageView

    private var isPasswordVisible = false
    private var email: String = ""
    private var password: String = ""

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Проверяем сохранённый токен
        if (getAuthToken() != null) {
            goToMain()
            return
        }

        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.edit_text1)
        passwordEditText = findViewById(R.id.edit_text2)
        loginButton = findViewById(R.id.button_1)
        googleLoginButton = findViewById(R.id.button_2)
        registerTextView = findViewById(R.id.btn_register)
        passwordVisibilityToggle = findViewById(R.id.image_view1)

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (!isValidInfo(email, password)) return@setOnClickListener
            login(email, password)
        }

        googleLoginButton.setOnClickListener { loginWithGoogle() }
        registerTextView.setOnClickListener { navigateToRegister() }
        passwordVisibilityToggle.setOnClickListener { togglePasswordVisibility() }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            email = emailEditText.text.toString().trim()
            password = passwordEditText.text.toString().trim()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun isValidInfo(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Некорректный email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun login(email: String, password: String) {
        userViewModel.authenticate(email, password).observe(this) { user ->
            if (user != null) {
                val token = generateTokenForUser(user.email)
                saveAuthToken(token)
                goToMain()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Ошибка входа")
                    .setMessage("Неверный email или пароль")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    private fun loginWithGoogle() {
        // Здесь должен быть Google Sign-In
        val fakeToken = generateTokenForUser("google_user")
        saveAuthToken(fakeToken)
        goToMain()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, SignUpStep1Activity::class.java)
        startActivity(intent)
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            passwordEditText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordVisibilityToggle.setImageResource(R.drawable.ic_eye)
        } else {
            passwordEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordVisibilityToggle.setImageResource(R.drawable.ic_eye)
        }
        passwordEditText.setSelection(passwordEditText.text.length)
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // ✅ Генерация "токена"
    private fun generateTokenForUser(email: String): String {
        return email.hashCode().toString() + System.currentTimeMillis().toString()
    }

    // ✅ Сохранение токена
    private fun saveAuthToken(token: String) {
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("access_token", token).apply()
    }

    // ✅ Получение токена
    private fun getAuthToken(): String? {
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return prefs.getString("access_token", null)
    }
}
