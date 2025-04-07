package com.example.drivenext.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.drivenext.R
import com.example.drivenext.ui.activity.MainActivity
import com.example.drivenext.viewmodel.CarViewModel
import java.text.SimpleDateFormat
import java.util.*

class CarRentFragment : Fragment(R.layout.fragment_rent_car) {

    private val carViewModel: CarViewModel by viewModels()

    private var carId: Int = -1
    private var rentDays: Int = 1
    private var dayString: String = "дней:"
    private var pricePerDay: Int = 0
    private val insurancePerDay: Int = 300
    private val deposit: Int = 15000

    private var startDate: Calendar = Calendar.getInstance()
    private var endDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carId = arguments?.getInt("car_id") ?: -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn = view.findViewById<ImageButton>(R.id.button_back)
        val nextBtn = view.findViewById<View>(R.id.btn_next)
        val startDateText = view.findViewById<TextView>(R.id.text_start_date)
        val endDateText = view.findViewById<TextView>(R.id.text_end_date)

        val rentDaysText = view.findViewById<TextView>(R.id.days_rent_summary)
        val insuranceDaysText = view.findViewById<TextView>(R.id.days_insurance)
        val rentPriceText = view.findViewById<TextView>(R.id.text_rent_price)
        val insurancePriceText = view.findViewById<TextView>(R.id.text_insurance_price)
        val totalPriceText = view.findViewById<TextView>(R.id.text_total_amount)
        val addressText = view.findViewById<TextView>(R.id.text_address)

        (activity as? MainActivity)?.setBottomNavVisibility(false)

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SuccessfulRentFragment())
                .addToBackStack(null)
                .commit()
        }

        // Выбор даты начала
        startDateText.setOnClickListener {
            showDatePicker(startDate) { calendar ->
                startDate = calendar
                updateDatesAndPrices(startDateText, endDateText, rentDaysText, insuranceDaysText, rentPriceText, insurancePriceText, totalPriceText)
            }
        }

        // Выбор даты конца
        endDateText.setOnClickListener {
            showDatePicker(endDate) { calendar ->
                endDate = calendar
                updateDatesAndPrices(startDateText, endDateText, rentDaysText, insuranceDaysText, rentPriceText, insurancePriceText, totalPriceText)
            }
        }

        carViewModel.getCarById(carId).observe(viewLifecycleOwner) { car ->
            car?.let {
                pricePerDay = car.pricePerDay
                addressText.text = car.address ?: "Адрес не указан"

                // Подставим в карточку (include): текст и картинку
                view.findViewById<TextView>(R.id.text_model).text = car.model
                view.findViewById<TextView>(R.id.text_brand).text = car.brand
                view.findViewById<TextView>(R.id.text_price).text = "${car.pricePerDay}₽"
                view.findViewById<TextView>(R.id.text_transmission).text = car.transmission
                view.findViewById<TextView>(R.id.text_fuel).text = car.fuelType
                Glide.with(this)
                    .load(car.imageResId)
                    .into(view.findViewById(R.id.image_car))

                updateDatesAndPrices(startDateText, endDateText, rentDaysText, insuranceDaysText, rentPriceText, insurancePriceText, totalPriceText)
            }
        }
    }

    private fun showDatePicker(defaultDate: Calendar, onDateSet: (Calendar) -> Unit) {
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val picked = Calendar.getInstance()
            picked.set(year, month, dayOfMonth)
            onDateSet(picked)
        },
            defaultDate.get(Calendar.YEAR),
            defaultDate.get(Calendar.MONTH),
            defaultDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDatesAndPrices(
        startDateText: TextView,
        endDateText: TextView,
        rentDaysText: TextView,
        insuranceDaysText: TextView,
        rentPriceText: TextView,
        insurancePriceText: TextView,
        totalPriceText: TextView
    ) {
        val format = SimpleDateFormat("HH:mm, dd MMMM yyyy", Locale("ru"))
        startDateText.text = format.format(startDate.time)
        endDateText.text = format.format(endDate.time)

        val diffMillis = endDate.timeInMillis - startDate.timeInMillis
        rentDays = (diffMillis / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(1)

        val lastDigit = rentDays % 10
        val lastTwoDigits = rentDays % 100

        dayString = when {
            rentDays == 1 -> "день:"
            lastTwoDigits in 11..14 -> "дней:"
            lastDigit == 1 -> "день:"
            lastDigit in 2..4 -> "дня:"
            else -> "дней:"
        }

        rentDaysText.text = "x$rentDays $dayString"
        insuranceDaysText.text = "x$rentDays $dayString"

        val rentTotal = pricePerDay * rentDays
        val insuranceTotal = insurancePerDay * rentDays
        val total = rentTotal + insuranceTotal

        rentPriceText.text = "${rentTotal}₽"
        insurancePriceText.text = "${insuranceTotal}₽"
        totalPriceText.text = "${total}₽"
    }

    companion object {
        fun newInstance(carId: Int): CarRentFragment {
            val fragment = CarRentFragment()
            fragment.arguments = Bundle().apply {
                putInt("car_id", carId)
            }
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment !is SuccessfulRentFragment) {
            (activity as? MainActivity)?.setBottomNavVisibility(true)
        }
    }

}
