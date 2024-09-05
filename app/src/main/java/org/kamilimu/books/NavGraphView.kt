package org.kamilimu.books

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import org.kamilimu.books.screens.books.BookDetailScreen
import org.kamilimu.books.screens.books.BooksScreen
import org.kamilimu.books.screens.books.models.Book
import org.kamilimu.books.screens.saved_books.SavedBooksScreen
import org.kamilimu.books.screens.settings.SettingsScreen

@Composable
fun NavGraphView(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Nav.Books.route
    ) {
        composable(
            route = Nav.Books.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Nav.BookDetail.route ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(4700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Nav.BookDetail.route ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(4700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Nav.BookDetail.route ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(4700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Nav.BookDetail.route ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            BooksScreen { book ->
                val json = Gson().toJson(book)
                navController.navigate("${Nav.BookDetail.route}/$json")
            }
        }
        composable(
            route = "${Nav.BookDetail.route}/{book}",
            arguments = listOf(
                navArgument("book") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val json = navBackStackEntry.arguments?.getString("book")
            val book = Gson().fromJson(json, Book::class.java)

            BookDetailScreen(book = book)
        }
        composable(
            route = Nav.SavedBooks.route,
        ) {
            SavedBooksScreen { book ->
                val json = Gson().toJson(book)
                navController.navigate("${Nav.BookDetail.route}/$json")
            }
        }
        composable(
            route = Nav.Settings.route,
        ) {
            SettingsScreen()
        }
    }
}