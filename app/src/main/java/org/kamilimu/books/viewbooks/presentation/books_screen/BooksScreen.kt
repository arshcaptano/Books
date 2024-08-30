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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.kamilimu.books.bookmarks.presentation.bookmarkScreen.BookmarkViewModel
import org.kamilimu.books.bookmarks.presentation.bookmarkScreen.FavouritesScreen
import org.kamilimu.books.util.ScreenNames


/**
 * Contains the [NavHost] of the app where all destinations in the are defined in the NavGraph
 * @param booksViewModel an instance of [BookViewModel] injected as a dependency
 * @param bookmarksViewModel an instance of the [BookmarkViewModel] injected as a dependency
 * @param navController used to control navigation across different screens as defined in
 * the [NavHost] navGraph
 */
@Composable
fun BooksScreen(
    booksViewModel: BookViewModel = hiltViewModel(),
    bookmarksViewModel: BookmarkViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val booksUiState by booksViewModel.booksState.collectAsStateWithLifecycle()
    val bookmarksUiState by bookmarksViewModel.bookmarkState.collectAsStateWithLifecycle()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = when {
        backStackEntry?.destination?.route == ScreenNames.HomeScreen.name -> ScreenNames.HomeScreen
        backStackEntry?.destination?.route == ScreenNames.FavouritesScreen.name -> ScreenNames.FavouritesScreen
        backStackEntry?.destination?.route?.startsWith("bookDetails/") == true -> ScreenNames.BookDetailsScreen
        else -> ScreenNames.HomeScreen
    }

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
                onScreenInFocus = booksViewModel::syncBookmarkedBooksInBookHomeScreen,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        composable(route = ScreenNames.FavouritesScreen.name) {
            FavouritesScreen(
                bookmarksUiState = bookmarksUiState,
                currentScreen = currentScreen,
                navController = navController,
                onAddBookmark = bookmarksViewModel::onAddBookmark,
                onRemoveBookmark = bookmarksViewModel::onDeleteBookmark,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        composable(
            route = "bookDetails/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            BookDetailsScreen(
                navController = navController,
                getBookDetails = bookmarksViewModel::getBookmarkById,
                bookId = bookId!!,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}