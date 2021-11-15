package com.example.manifestofinal

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Manifesto: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
