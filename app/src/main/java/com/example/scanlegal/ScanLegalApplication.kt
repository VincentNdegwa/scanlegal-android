package com.example.scanlegal

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScanLegalApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}
