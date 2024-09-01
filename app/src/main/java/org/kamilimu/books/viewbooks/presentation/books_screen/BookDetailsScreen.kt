package org.kamilimu.books.viewbooks.presentation.books_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.kamilimu.books.bookmarks.presentation.bookmarkScreen.BookDetailsContent
import org.kamilimu.books.util.components.DetailsScreenAppBar
import org.kamilimu.books.util.components.ErrorScreen
import org.kamilimu.books.util.components.LoadingScreen
import org.kamilimu.books.viewbooks.domain.model.Book

@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bookDetailsState: BooksViewState,
    onFavouriteClicked: (Book) -> Unit,
    updateBookState: (Book) -> Unit
) {
    Scaffold(
        topBar = { DetailsScreenAppBar(navController = navController) },
        modifier = modifier
    ) { innerPadding ->
        when (bookDetailsState) {
            is BooksViewState.Loading -> {
                LoadingScreen()
            }

            is BooksViewState.Failure -> {
                ErrorScreen(
                    message = bookDetailsState.message
                )
            }

            is BooksViewState.Success -> {
                val book =
                    bookDetailsState.books.first()  // Get the first and only Book in the list
                BookDetailsContent(
                    title = book.title,
                    subjects = book.subjects,
                    authors = book.authors,
                    translators = book.translators,
                    bookshelves = book.bookshelves,
                    languages = book.languages,
                    mediaType = book.mediaType,
                    image = book.formats.jpeg,
                    downloadCount = book.downloadCount,
                    isBookmarked = book.isBookmarked,
                    onFavouriteClicked = {
                        onFavouriteClicked(book)
                        updateBookState(book)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}