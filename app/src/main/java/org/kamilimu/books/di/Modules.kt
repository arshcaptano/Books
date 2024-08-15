package org.kamilimu.books.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.kamilimu.books.database.provideBookDao
import org.kamilimu.books.database.provideRoomDatabase
import org.kamilimu.books.network.provideApi
import org.kamilimu.books.network.provideOkHttpClient
import org.kamilimu.books.network.provideRetrofit
import org.kamilimu.books.screens.bookmarks.BookmarksViewModel
import org.kamilimu.books.screens.books.BooksRepository
import org.kamilimu.books.screens.books.BooksViewModel
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
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
    single { provideRoomDatabase(androidContext(), get()) }
    single { provideBookDao(get()) }
}

val booksModule: Module = module {
    single { BooksRepository(get()) }
    viewModel { BooksViewModel(get()) }
}

val bookmarksModule: Module = module {
    viewModel { BookmarksViewModel(get()) }
}