package org.kamilimu.books.screens.saved_books

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.screens.books.BooksViewModel
import org.kamilimu.books.screens.books.db.BookEntity
import org.kamilimu.books.shared.components.BooksContentScreen
import org.kamilimu.books.shared.components.LottieView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedBooksScreen(
    onItemSelect: (BookEntity) -> Unit
) {
    val vm: BooksViewModel = koinViewModel()
    val screenState = vm.screenState.collectAsState()
    val books = screenState.value.books
    val errorMessage = screenState.value.errorMessage
    val isLoading = screenState.value.isLoading

    LaunchedEffect(Unit) {
        vm.observeSavedBooks()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Saved Books"
        )

        if (errorMessage.isEmpty()) {
            Column {
                if (isLoading) {
                    LottieView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        animationFile = "loading.json"
                    )
                } else {
                    BooksContentScreen(
                        books = books,
                        onItemSelect = { onItemSelect.invoke(it) },
                        onSaveBook = { vm.saveBook(it.id, !it.isSaved) }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = errorMessage
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SavedBooksScreenPreview() {
    val mockBooks = listOf(
        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
    )

    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SavedBooksScreenDarkPreview() {
    val mockBooks = listOf(
        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
    )

    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
}