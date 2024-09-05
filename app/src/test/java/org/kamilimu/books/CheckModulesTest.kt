package org.kamilimu.books

import org.junit.Test
import org.kamilimu.books.di.savedBooksModule
import org.kamilimu.books.di.booksModule
import org.kamilimu.books.di.dbModule
import org.kamilimu.books.di.networkModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkNetworkModule() {
        networkModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkDbModule() {
        dbModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkBooksModule() {
        booksModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkSavedBooksModule() {
        savedBooksModule.verify()
    }
}