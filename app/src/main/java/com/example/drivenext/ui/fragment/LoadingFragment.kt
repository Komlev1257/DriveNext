package com.example.drivenext.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.drivenext.R

class LoadingFragment : Fragment() {

    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = arguments?.getString("search_query")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gifView = view.findViewById<ImageView>(R.id.gif_loader)

        Glide.with(requireContext())
            .asGif()
            .load(R.drawable.ic_loading)
            .override(300, 100)
            .into(gifView)


        Handler(Looper.getMainLooper()).postDelayed({
            openSearchFragment()
        }, 2000)
    }

    private fun openSearchFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SearchFragment.newInstance(searchQuery ?: ""))
            .commit()
    }

    companion object {
        fun newInstance(query: String): LoadingFragment {
            val fragment = LoadingFragment()
            val args = Bundle()
            args.putString("search_query", query)
            fragment.arguments = args
            return fragment
        }
    }
}
