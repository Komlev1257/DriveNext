package com.example.drivenext.data

sealed class SettingsItem {
    data class Profile(
        val name: String,
        val email: String,
        val avatarPath: String
    ) : SettingsItem()
    data class Option(val iconRes: Int, val label: String) : SettingsItem()
}