package com.example.drivenext.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.drivenext.R
import com.example.drivenext.viewmodel.CarViewModel

class CarDetailsFragment : Fragment(R.layout.fragment_car_details) {

    private val carViewModel: CarViewModel by viewModels()
    private var carId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carId = arguments?.getInt(ARG_CAR_ID) ?: -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carImage = view.findViewById<ImageView>(R.id.image_car)
        val modelText = view.findViewById<TextView>(R.id.text_model)
        val locationText = view.findViewById<TextView>(R.id.text_location_value)
        val descriptionText = view.findViewById<TextView>(R.id.text_description_value)
        val priceText = view.findViewById<TextView>(R.id.text_price)
        val backButton = view.findViewById<ImageButton>(R.id.back_button)

        // Назад
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Загрузка машины по id
        carViewModel.getCarById(carId).observe(viewLifecycleOwner) { car ->
            car?.let {
                modelText.text = "${it.model}"
                locationText.text = it.address ?: "Адрес не указан"
                descriptionText.text = it.description ?: "Описание отсутствует"
                priceText.text = "${it.pricePerDay}₽/день"

                if (it.imageResId != 0) {
                    Glide.with(requireContext())
                        .load(it.imageResId)
                        .into(carImage)
                }
            }
        }
    }

    companion object {
        private const val ARG_CAR_ID = "car_id"

        fun newInstance(carId: Int): CarDetailsFragment {
            val fragment = CarDetailsFragment()
            val args = Bundle()
            args.putInt(ARG_CAR_ID, carId)
            fragment.arguments = args
            return fragment
        }
    }
}
