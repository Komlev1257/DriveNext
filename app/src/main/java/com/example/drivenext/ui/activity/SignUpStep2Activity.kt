package com.example.drivenext.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drivenext.R
import java.util.Calendar


class SignUpStep2Activity : AppCompatActivity() {

    private lateinit var editTextLastName: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextMiddleName: EditText
    private lateinit var editTextBirthday: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private lateinit var btnNext: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        // Инициализация элементов
        editTextLastName = findViewById(R.id.edit_text1)
        editTextFirstName = findViewById(R.id.edit_text2)
        editTextMiddleName = findViewById(R.id.edit_text3)
        editTextBirthday = findViewById(R.id.edit_text4)
        radioGroupGender = findViewById(R.id.radioGroup)
        radioMale = findViewById(R.id.radioMale)
        radioFemale = findViewById(R.id.radioFemale)
        btnNext = findViewById(R.id.btn_next)

        // Обработка клика на кнопку "Next"
        btnNext.setOnClickListener {
            val lastName = editTextLastName.text.toString().trim()
            val firstName = editTextFirstName.text.toString().trim()
            val middleName = editTextMiddleName.text.toString().trim()
            val birthday = editTextBirthday.text.toString().trim()
            val selectedGender = if (radioMale.isChecked) "Male" else "Female"

            // Проверка валидности введённых данных
            if (lastName.isEmpty() || firstName.isEmpty() || middleName.isEmpty() || birthday.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // В следующем шаге можно передать данные, если необходимо
                val intent = Intent(this, SignUpStep3Activity::class.java).apply {
                    putExtra("LAST_NAME", lastName)
                    putExtra("FIRST_NAME", firstName)
                    putExtra("MIDDLE_NAME", middleName)
                    putExtra("BIRTHDAY", birthday)
                    putExtra("GENDER", selectedGender)
                }
                startActivity(intent)
            }
        }

        // Обработка клика на поле ввода даты рождения
        editTextBirthday.setOnClickListener {
            openDatePicker()
        }

        // Обработка кнопки "Назад"
        findViewById<View>(R.id.back_button).setOnClickListener {
            onBackPressed() // Возвращает на предыдущий экран
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Открытие DatePickerDialog для выбора даты
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editTextBirthday.setText(formattedDate) // Устанавливаем выбранную дату в поле
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}

