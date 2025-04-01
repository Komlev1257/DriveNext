package com.example.drivenext.ui.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.drivenext.R
import com.example.drivenext.ui.fragment.HomeFragment
import com.example.drivenext.ui.fragment.FavouritesFragment
import com.example.drivenext.ui.fragment.SettingsFragment

class MainActivity : BaseActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)

        // По умолчанию открываем HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            updateBottomIcons(R.id.nav_home)
        }

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
                updateBottomIcons(item.itemId)
                true
            } ?: false
        }
    }

    private fun updateBottomIcons(selectedItemId: Int) {
        val menu = bottomNav.menu

        menu.findItem(R.id.nav_home).icon =
            ContextCompat.getDrawable(this, if (selectedItemId == R.id.nav_home) R.drawable.ic_home_active else R.drawable.ic_home)

        menu.findItem(R.id.nav_bookings).icon =
            ContextCompat.getDrawable(this, if (selectedItemId == R.id.nav_bookings) R.drawable.ic_bookmark_active else R.drawable.ic_bookmark)

        menu.findItem(R.id.nav_settings).icon =
            ContextCompat.getDrawable(this, if (selectedItemId == R.id.nav_settings) R.drawable.ic_settings_active else R.drawable.ic_settings)
    }
}
