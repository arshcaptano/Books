package org.kamilimu.books.viewbooks.presentation.books_screen

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
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.presentation.books_screen.components.BookCard
import org.kamilimu.books.viewbooks.presentation.books_screen.components.ErrorScreen
import org.kamilimu.books.viewbooks.presentation.books_screen.components.LoadingScreen
import org.kamilimu.books.viewbooks.presentation.util.ScreenNames
import org.kamilimu.books.viewbooks.presentation.util.components.BooksBottomBar
import org.kamilimu.books.viewbooks.presentation.util.components.BooksTopBar

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    bookmarkedBooks: BooksViewState,
    currentScreen: ScreenNames,
    navController: NavHostController,
    onFavouriteClicked: (Book) -> Unit
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
                    navController.navigate(ScreenNames.FavouritesScreen.name)
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (bookmarkedBooks) {
                is BooksViewState.Loading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }
                is BooksViewState.Failure -> {
                    ErrorScreen(
                        message = bookmarkedBooks.message,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is BooksViewState.Success -> {
                    LazyColumn {
                        items(bookmarkedBooks.books) { book ->
                            BookCard(
                                book = book,
                                navController = navController,
                                onFavouriteClicked = { onFavouriteClicked(book) },
                                onCardClicked = {},
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