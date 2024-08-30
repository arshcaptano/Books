package org.kamilimu.books.bookmarks.presentation.bookmarkScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import org.kamilimu.books.R
import org.kamilimu.books.util.ScreenNames
import org.kamilimu.books.util.components.BookCard
import org.kamilimu.books.util.components.BooksBottomBar
import org.kamilimu.books.util.components.BooksTopBar
import org.kamilimu.books.util.components.ErrorScreen
import org.kamilimu.books.util.components.LoadingScreen
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.presentation.books_screen.BooksViewState

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    bookmarksUiState: BooksViewState,
    currentScreen: ScreenNames,
    navController: NavHostController,
    onAddBookmark: (Book) -> Unit,
    onRemoveBookmark: (Book) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BooksTopBar(title = currentScreen.title)
        },
        bottomBar = {
            BooksBottomBar(
                currentScreen = currentScreen,
                onHomeClicked = {
                    navController.navigate(ScreenNames.HomeScreen.name)
                },
                onFavoritesClicked = {
                    if (currentScreen == ScreenNames.FavouritesScreen)
                        navController.navigate(ScreenNames.FavouritesScreen.name) {
                            launchSingleTop = true
                        }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (bookmarksUiState) {
                is BooksViewState.Loading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }

                is BooksViewState.Failure -> {
                    ErrorScreen(
                        message = bookmarksUiState.message,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is BooksViewState.Success -> {
                    LazyColumn {
                        items(bookmarksUiState.books) { book ->
                            BookCard(
                                book = book,
                                onFavouriteClicked = {
                                    if (book.isBookmarked)
                                        onRemoveBookmark(book) else onAddBookmark(book)
                                },
                                onCardClicked = {
                                    val route = "bookDetails/${book.id}"
                                    navController.navigate(route)
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(dimensionResource(R.dimen.padding_medium))
                            )
                        }
                    }
                }
            }
        }
    }
}