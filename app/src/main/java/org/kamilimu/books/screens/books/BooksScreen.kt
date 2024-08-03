package org.kamilimu.books.screens.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.shared.components.LottieView
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(vm: BooksViewModel = koinViewModel()) {
    val screenState = vm.screenState.collectAsState()
    val books = screenState.value.books
    val errorMessage = screenState.value.errorMessage
    val isLoading = screenState.value.isLoading

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
            if (errorMessage.isEmpty()) {
                Column(modifier = Modifier.padding(contentPadding)) {
                    if (isLoading) {
                        LottieView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            animationFile = "loading.json"
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxHeight()) {
                            items(books) { book ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                        ) {
                                            Text(
                                                book.title,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(16.dp))

                                    }
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            } else {
                Text(errorMessage)
            }
        }
    )
}

@Preview(
    name = "Game Screen Light Mode",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BooksScreenPreview() {
    BooksScreen()
}