package com.example.drivenext.ui.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drivenext.R
import com.example.drivenext.data.Car
import com.example.drivenext.data.CarAdapter
import com.example.drivenext.viewmodel.CarViewModel

class HomeFragment : Fragment(R.layout.fragment_homepage) {

    private val carViewModel: CarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCarsIfNeeded()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_cars)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.bottom = 50
            }
        })

        carViewModel.allCars.observe(viewLifecycleOwner) { cars ->
            recyclerView.adapter = CarAdapter(cars)
        }

        val editSearch = view.findViewById<EditText>(R.id.edit_search)
        editSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val query = editSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    val loadingFragment = LoadingFragment.newInstance(query)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, loadingFragment)
                        .addToBackStack(null)
                        .commit()
                }
                true
            } else {
                false
            }
        }

    }

    private fun initCarsIfNeeded() {
        val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val alreadyInitialized = prefs.getBoolean("cars_initialized", false)

        if (!alreadyInitialized) {
            val starterCars = listOf(
                Car(model="S 500 Sedan", brand="Mercedes-Benz", pricePerDay=2200, transmission="М/T", fuelType="Бензин", imageResId=R.drawable.ic_iris),
                Car(model="S 500 Sedan", brand="Mercedes-Benz", pricePerDay=2800, transmission="A/T", fuelType="Дизель", imageResId=R.drawable.ic_iris),
                Car(model="S 500 Sedan", brand="Mercedes-Benz", pricePerDay=2400, transmission="М/T", fuelType="Дизель", imageResId=R.drawable.ic_iris),
                Car(model="S 500 Sedan", brand="Mercedes-Benz", pricePerDay=2500, transmission="A/T", fuelType="Бензин", imageResId=R.drawable.ic_iris)
            )

            carViewModel.insertAll(starterCars)
            prefs.edit().putBoolean("cars_initialized", true).apply()
        }
    }
}
