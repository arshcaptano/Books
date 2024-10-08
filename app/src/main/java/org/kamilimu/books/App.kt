package org.kamilimu.books

import android.app.Application
import org.kamilimu.books.di.SettingsViewModel
import org.kamilimu.books.di.savedBooksModule
import org.kamilimu.books.di.booksModule
import org.kamilimu.books.di.dataStoreModule
import org.kamilimu.books.di.dbModule
import org.kamilimu.books.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(networkModule + dbModule + dataStoreModule + booksModule + savedBooksModule + SettingsViewModel)
        }
    }
}