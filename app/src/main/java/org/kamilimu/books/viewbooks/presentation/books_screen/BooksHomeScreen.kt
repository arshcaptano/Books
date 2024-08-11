package org.kamilimu.books.viewbooks.presentation.books_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun BooksHomeScreen(
    modifier: Modifier = Modifier,
    booksUiState: BooksViewState,
    currentScreen: ScreenNames,
    navController: NavHostController,
    onFavouriteClicked: (Book) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar  = {
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (booksUiState) {
                is BooksViewState.Loading -> {
                    LoadingScreen()
                }
                is BooksViewState.Failure -> {
                    ErrorScreen(
                        message = booksUiState.message,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is BooksViewState.Success -> {
                    LazyColumn() {
                        items(booksUiState.books) { book ->
                            BookCard(
                                book = book,
                                navController = navController,
                                onFavouriteClicked = { onFavouriteClicked(book) },
                                onCardClicked = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_medium))
                            )
                        }
                    }
                }
            }
        }
    }
}