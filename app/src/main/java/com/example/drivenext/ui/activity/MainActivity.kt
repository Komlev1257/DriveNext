package com.example.drivenext.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.drivenext.R
import com.example.drivenext.ui.fragment.HomeFragment
import com.example.drivenext.ui.fragment.FavouritesFragment
import com.example.drivenext.ui.fragment.SettingsFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // По умолчанию открываем HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_bookings -> FavouritesFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
                true
            } ?: false
        }
    }
}
