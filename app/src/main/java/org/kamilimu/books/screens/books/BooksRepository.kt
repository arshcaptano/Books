package org.kamilimu.books.screens.books

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kamilimu.books.network.Api
import org.kamilimu.books.screens.books.models.Book

class BooksRepository(private val api: Api) {
    private val superVisor = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + superVisor)

    fun getBooks(
        onSuccess: (List<Book>) -> Unit,
        onFail: (errorMessage: String) -> Unit
    ) {
        val handler = CoroutineExceptionHandler { _, exception ->
            onFail(exception.message ?: "Error")
        }

        scope.launch(handler) {
            try {
                val response = withContext(Dispatchers.IO) {
                    api.getBooks()
                }

                onSuccess(response.books)
            } catch (exception: Exception) {
                onFail(exception.message ?: "Error")
            }
        }
    }
}