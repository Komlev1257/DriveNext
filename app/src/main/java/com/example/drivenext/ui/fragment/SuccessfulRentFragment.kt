package com.example.drivenext.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.drivenext.R
import com.example.drivenext.ui.activity.MainActivity

class SuccessfulRentFragment : Fragment(R.layout.fragment_successful_rent) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goHomeButton = view.findViewById<Button>(R.id.btn_next)
        val goToBookingsText = view.findViewById<TextView>(R.id.text_go_to_bookings)
        (activity as? MainActivity)?.setBottomNavVisibility(false)

        // Кнопка "Домой"
        goHomeButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Ссылка "Перейти к своим бронированиям"
        goToBookingsText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setBottomNavVisibility(true)
    }
}
