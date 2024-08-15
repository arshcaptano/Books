package org.kamilimu.books.screens.books

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.screens.books.models.Book
import org.kamilimu.books.shared.components.LottieView
import org.koin.androidx.compose.koinViewModel

@Composable
fun BooksScreen(
    vm: BooksViewModel = koinViewModel(),
    onItemSelect: (Book) -> Unit
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
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            book.title,
                                            maxLines = 1,
                                            overflow = TextOverflow.Clip
                                        )

                                        Spacer(modifier = Modifier.width(32.dp))

                                        Surface(
                                            shape = CircleShape,
                                            modifier = Modifier
                                                .size(32.dp),
                                            color = Color(0x77000000),
                                        ) {
                                            BookmarkIcon(modifier = Modifier.padding(8.dp))
                                        }
                                    }
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
                scaleX = 1.3f
                scaleY = 1.3f
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BooksScreenPreview() {
    BooksScreen{}
}