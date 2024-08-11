package org.kamilimu.books.viewbooks.presentation.books_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.kamilimu.books.viewbooks.presentation.util.ScreenNames


/**
 * Contains the [NavHost] of the app where all destinations in the are defined in the NavGraph
 * @param booksViewModel an instance of [BookViewModel] injected as a dependency
 * @param navController used to control navigation across different screens as defined in
 * the [NavHost] navGraph
 */
@Composable
fun BooksScreen(
    booksViewModel: BookViewModel =  hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val booksUiState by booksViewModel.booksState.collectAsStateWithLifecycle()
    val bookmarkedBooks by booksViewModel.bookmarkedBooks.collectAsStateWithLifecycle()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ScreenNames.valueOf(
        backStackEntry?.destination?.route ?: ScreenNames.HomeScreen.name
    )

    NavHost(
        navController = navController,
        startDestination = ScreenNames.HomeScreen.name
    ) {
        composable(route = ScreenNames.HomeScreen.name) {
            BooksHomeScreen(
                booksUiState = booksUiState,
                currentScreen = currentScreen,
                navController = navController,
                onFavouriteClicked = booksViewModel::onFavouriteClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        composable(route = ScreenNames.FavouritesScreen.name) {
            FavouritesScreen(
                bookmarkedBooks = bookmarkedBooks,
                currentScreen = currentScreen,
                navController = navController,
                onFavouriteClicked = booksViewModel::onFavouriteClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        composable(route = ScreenNames.BookDetailsScreen.name) {
            BookDetailsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}