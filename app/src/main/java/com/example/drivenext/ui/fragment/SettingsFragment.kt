package com.example.drivenext.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drivenext.R
import com.example.drivenext.data.SettingsAdapter
import com.example.drivenext.data.SettingsItem
import com.example.drivenext.data.SettingsItemDecoration
import com.example.drivenext.viewmodel.UserViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_settings)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.addItemDecoration(SettingsItemDecoration(10, 50))

        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = prefs.getString("user_email", null)

        if (email != null) {
            userViewModel.getUserByEmail(email).observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    val settingsItems = listOf(
                        SettingsItem.Profile(
                            name = "${user.firstName} ${user.lastName}",
                            email = user.email,
                            avatarPath = user.profilePic
                        ),
                        SettingsItem.Option(R.drawable.ic_bookings, "Мои бронирования"),
                        SettingsItem.Option(R.drawable.ic_theme, "Тема"),
                        SettingsItem.Option(R.drawable.ic_notifications, "Уведомления"),
                        SettingsItem.Option(R.drawable.ic_banknotes, "Подключить свой автомобиль"),
                        SettingsItem.Option(R.drawable.ic_help, "Помощь"),
                        SettingsItem.Option(R.drawable.ic_share, "Пригласи друга")
                    )

                    recycler.adapter = SettingsAdapter(settingsItems) { item ->
                        when (item) {
                            is SettingsItem.Option -> {
                                Toast.makeText(requireContext(), "Нажали: ${item.label}", Toast.LENGTH_SHORT).show()
                            }
                            is SettingsItem.Profile -> {
                                Toast.makeText(requireContext(), "Профиль: ${item.name}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
