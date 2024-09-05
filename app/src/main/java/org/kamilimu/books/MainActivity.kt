package org.kamilimu.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.kamilimu.books.screens.books.db.BookEntity
import org.kamilimu.books.screens.books.BooksContentScreen
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
                                    Surface(
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .padding(start = 16.dp, end = 8.dp)
                                            .size(24.dp),
                                        onClick = {
                                            navController.navigate(Nav.Settings.name)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Settings,
                                            contentDescription = null
                                        )
                                    }

                                    Text(text = "Books")

                                    Surface(
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .padding(end = 36.dp)
                                            .size(24.dp),
                                        onClick = {
                                            navController.navigate(Nav.SavedBooks.name)
                                        }
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_bookmark_active),
                                            contentDescription = stringResource(id = R.string.saved_books)
                                        )
                                    }
                                }
                            })
                    },
                    content = { contentPadding ->
                        Column(modifier = Modifier.padding(contentPadding)) {
                            NavGraphView(navController = navController)
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
                    val mockBooks = listOf(
                        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
                        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
                    )

                    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
                }
            }
        )
    }
}