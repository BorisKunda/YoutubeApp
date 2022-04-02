package com.boris.youtubeapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class YoutubeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}