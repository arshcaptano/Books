package org.kamilimu.books.screens.books

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.screens.books.db.BookEntity
import org.kamilimu.books.shared.components.LottieView
import org.koin.androidx.compose.koinViewModel

@Composable
fun BooksScreen(
    onItemSelect: (BookEntity) -> Unit
) {
    val vm: BooksViewModel = koinViewModel()
    val screenState = vm.screenState.collectAsState()
    val books = screenState.value.books
    val errorMessage = screenState.value.errorMessage
    val isLoading = screenState.value.isLoading

    LaunchedEffect(Unit) {
        vm.fetchBooks()
    }

    if (errorMessage.isEmpty()) {
        Box {
            BooksContentScreen(
                books = books,
                onItemSelect = { onItemSelect.invoke(it) },
                onSaveBook = { vm.saveBook(bookId = it.id, save = !it.isSaved) }
            )
            if (isLoading) {
                LottieView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    animationFile = "loading.json"
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

@Preview(
    name = "Books Screen Light Mode",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BooksScreenPreview() {
    val mockBooks = listOf(
        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
    )

    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
}

@Preview(
    name = "Books Screen Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BooksScreenDarkPreview() {
    val mockBooks = listOf(
        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
    )

    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
}