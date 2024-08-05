package org.kamilimu.books

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}