package org.kamilimu.books.screens.books

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.R
import org.kamilimu.books.screens.books.db.BookEntity
import org.kamilimu.books.screens.books.models.Book
import org.kamilimu.books.shared.components.LottieView
import org.koin.androidx.compose.koinViewModel

@Composable
fun BooksScreen(
    vm: BooksViewModel = koinViewModel(),
    onItemSelect: (BookEntity) -> Unit
) {
    val screenState = vm.screenState.collectAsState()
    val books = screenState.value.books
    val errorMessage = screenState.value.errorMessage
    val isLoading = screenState.value.isLoading

    if (errorMessage.isEmpty()) {
        Column {
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
                                    .padding(16.dp)
                                    .clickable {
                                        onItemSelect(book)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                ) {
                                    BookItem(book = book)

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .size(32.dp)
                                            .padding(0.dp, 16.dp, 0.dp, 0.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = Color(0x77000000),
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .size(36.dp),
                                            onClick = {
                                                vm.saveBook(book.id)
                                            }
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = if (book.isSaved)
                                                        R.drawable.ic_bookmark_active
                                                    else
                                                        R.drawable.ic_bookmark
                                                ),
                                                contentDescription = stringResource(id = R.string.bookmarks)
                                            )

                                        }
                                    }
                                }
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

@Composable
fun BookmarkIcon(
    modifier: Modifier = Modifier,
    color: Color = Color(0xffbab6b6)
) {
    var isBookmarked by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = {
            isBookmarked = !isBookmarked
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.4f
                scaleY = 1.4f
            },
            imageVector = if (isBookmarked) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }
}

@Composable
fun BookItem(book: BookEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(text = book.title, style = MaterialTheme.typography.headlineSmall)

        Text(
            text = "Authors: ${book.authors.joinToString { it.name }}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Subjects: ${book.subjects.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BooksScreenPreview() {
    BooksScreen {}
}