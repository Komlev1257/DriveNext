package com.example.drivenext.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drivenext.R
import com.example.drivenext.data.CarAdapter
import com.example.drivenext.viewmodel.CarViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val carViewModel: CarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_cars)
        val buttonBack = view.findViewById<ImageView>(R.id.button_back)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.bottom = 50
            }
        })

        val query = arguments?.getString("search_query")?.trim().orEmpty()

        carViewModel.allCars.observe(viewLifecycleOwner) { cars ->
            val filtered = cars.filter {
                it.brand.contains(query, ignoreCase = true) ||
                        it.model.contains(query, ignoreCase = true)
            }

            recyclerView.adapter = CarAdapter(
                filtered,
                onDetailClick = { carId ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CarDetailsFragment.newInstance(carId))
                        .addToBackStack(null)
                        .commit()
                },
                onBookClick = { carId ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CarRentFragment.newInstance(carId))
                        .addToBackStack(null)
                        .commit()
                }
            )
        }

        buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(query: String): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle().apply {
                putString("search_query", query)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
