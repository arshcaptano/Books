package org.kamilimu.books.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.kamilimu.books.data.DataStoreRepository
import org.kamilimu.books.data.database.provideBookDao
import org.kamilimu.books.data.database.provideDatabase
import org.kamilimu.books.network.provideApi
import org.kamilimu.books.network.provideOkHttpClient
import org.kamilimu.books.network.provideRetrofit
import org.kamilimu.books.screens.books.BooksRepository
import org.kamilimu.books.screens.books.BooksViewModel
import org.kamilimu.books.screens.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val networkModule: Module = module {
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient() }
    factory { provideApi(get()) }
}

val dbModule: Module = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    single { provideDatabase(androidContext(), get()) }
    single { provideBookDao(get()) }
}

val dataStoreModule = module {
    single { DataStoreRepository(get()) }
    viewModel { SettingsViewModel(get()) }
}

val booksModule: Module = module {
    single { BooksRepository(get()) }
    viewModel { BooksViewModel(get(), get()) }
}

val savedBooksModule: Module = module {
    viewModel { BooksViewModel(get(), get()) }
}

val SettingsViewModel: Module = module {
    viewModel { SettingsViewModel(get()) }
}