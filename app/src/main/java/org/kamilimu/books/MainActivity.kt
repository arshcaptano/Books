package org.kamilimu.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import org.kamilimu.books.screens.Nav
import org.kamilimu.books.screens.saved_books.SavedBooksScreen
import org.kamilimu.books.screens.books.BookDetailScreen
import org.kamilimu.books.screens.books.BooksScreen
import org.kamilimu.books.screens.books.models.Book
import org.kamilimu.books.theme.BooksTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksTheme {
                val navController: NavHostController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Library")

                                    Spacer(modifier = Modifier.width(32.dp))

                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0x77000000),
                                        modifier = Modifier
                                            .padding(end = 40.dp)
                                            .size(24.dp),
                                        onClick = {
                                            navController.navigate(Nav.Bookmarks.name)
                                        }
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_bookmark_active),
                                            contentDescription = stringResource(id = R.string.bookmarks)
                                        )

                                    }
                                }
                            })
                    },
                    content = { contentPadding ->
                        Column(modifier = Modifier.padding(contentPadding)) {
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
                                    route = Nav.Bookmarks.route,
                                ) {
                                    SavedBooksScreen()
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BooksTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text("Books")
                        }
                    })
            },
            content = { contentPadding ->
                Column(modifier = Modifier.padding(contentPadding)) {
                    BooksScreen {}
                }
            }
        )
    }
}