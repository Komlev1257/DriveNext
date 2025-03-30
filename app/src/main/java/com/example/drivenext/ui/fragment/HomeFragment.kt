package com.example.drivenext.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drivenext.R
import com.example.drivenext.data.Car
import com.example.drivenext.data.CarAdapter

class HomeFragment : Fragment(R.layout.fragment_homepage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_cars)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.bottom = 50 // отступ снизу
            }
        })

        val cars = listOf(
            Car("S 500 Sedan", "Mercedes-Benz", 2500, "М/T", "Бензин", R.drawable.ic_iris),
            Car("S 500 Sedan", "Mercedes-Benz", 2200, "A/T", "Дизель", R.drawable.ic_iris),
            Car("S 500 Sedan", "Mercedes-Benz", 3000, "А/T", "Бензин", R.drawable.ic_iris)
        )

        recyclerView.adapter = CarAdapter(cars)
    }

}
