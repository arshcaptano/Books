package org.kamilimu.books.di

import org.kamilimu.books.network.provideApi
import org.kamilimu.books.network.provideOkHttpClient
import org.kamilimu.books.network.provideRetrofit
import org.kamilimu.books.screens.books.BooksRepository
import org.kamilimu.books.screens.books.BooksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val networkModule: Module = module {
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient() }
    factory { provideApi(get()) }
}

val booksModule: Module = module {
    single { BooksRepository(get()) }
    viewModel { BooksViewModel(get()) }
}