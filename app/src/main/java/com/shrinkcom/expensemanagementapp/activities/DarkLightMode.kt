package com.shrinkcom.expensemanagementapp.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class DarkLightMode : AppCompatActivity() {

    enum class DarkModeConfig{
        YES,
        NO,
        FOLLOW_SYSTEM
    }

    fun shouldEnableDarkMode(darkModeConfig: DarkModeConfig){
        when(darkModeConfig){
            DarkModeConfig.YES -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            DarkModeConfig.NO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DarkModeConfig.FOLLOW_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}