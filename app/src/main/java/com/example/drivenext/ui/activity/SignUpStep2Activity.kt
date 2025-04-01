package com.example.drivenext.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.drivenext.R
import java.util.*

class SignUpStep2Activity : BaseActivity() {

    private lateinit var editTextLastName: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextMiddleName: EditText
    private lateinit var editTextBirthday: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private lateinit var btnNext: View

    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        // Получаем данные из SignUpStep1
        email = intent.getStringExtra("email")
        password = intent.getStringExtra("password")

        editTextLastName = findViewById(R.id.edit_text1)
        editTextFirstName = findViewById(R.id.edit_text2)
        editTextMiddleName = findViewById(R.id.edit_text3)
        editTextBirthday = findViewById(R.id.edit_text4)
        radioGroupGender = findViewById(R.id.radioGroup)
        radioMale = findViewById(R.id.radioMale)
        radioFemale = findViewById(R.id.radioFemale)
        btnNext = findViewById(R.id.btn_next)

        btnNext.setOnClickListener {
            val lastName = editTextLastName.text.toString().trim()
            val firstName = editTextFirstName.text.toString().trim()
            val middleName = editTextMiddleName.text.toString().trim()
            val birthday = editTextBirthday.text.toString().trim()
            val selectedGender = if (radioMale.isChecked) "Male" else if (radioFemale.isChecked) "Female" else ""

            if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || selectedGender.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SignUpStep3Activity::class.java).apply {
                    putExtra("email", email)
                    putExtra("password", password)
                    putExtra("firstName", firstName)
                    putExtra("lastName", lastName)
                    putExtra("middleName", middleName)
                    putExtra("birthDate", birthday)
                    putExtra("gender", selectedGender)
                }
                startActivity(intent)
            }
        }

        editTextBirthday.setOnClickListener { openDatePicker() }

        findViewById<View>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                editTextBirthday.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
