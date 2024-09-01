package org.kamilimu.books.viewbooks.presentation.books_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
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

@Composable
fun BooksHomeScreen(
    modifier: Modifier = Modifier,
    booksUiState: BooksViewState,
    currentScreen: ScreenNames,
    onScreenInFocus: () -> Unit,
    navController: NavHostController,
    onFavouriteClicked: (Book) -> Unit,
    onCardClicked: (Int) -> Unit
) {
    val rememberedCurrentScreen by rememberUpdatedState(currentScreen)

    LaunchedEffect(rememberedCurrentScreen) {
        if (rememberedCurrentScreen == ScreenNames.HomeScreen)
            onScreenInFocus()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BooksTopBar(title = currentScreen.title)
        },
        bottomBar = {
            BooksBottomBar(
                currentScreen = currentScreen,
                onHomeClicked = {
                    if (currentScreen == ScreenNames.HomeScreen)
                        navController.navigate(ScreenNames.HomeScreen.name) {
                            launchSingleTop = true
                        }
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
                                onFavouriteClicked = { onFavouriteClicked(book) },
                                onCardClicked = {
                                    onCardClicked(book.id)
                                    navController.navigate(ScreenNames.BookDetailsScreen.name)
                                },
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