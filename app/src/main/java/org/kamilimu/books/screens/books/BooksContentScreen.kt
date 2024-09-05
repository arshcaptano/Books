package org.kamilimu.books.screens.books

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.R
import org.kamilimu.books.screens.books.db.BookEntity

@Composable
fun BooksContentScreen(
    books: List<BookEntity>,
    onItemSelect: (BookEntity) -> Unit,
    onSaveBook: (BookEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        itemsIndexed(books) { index, book ->
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
                        BookItem(
                            book = book,
                            onItemSelect = { onItemSelect.invoke(it) },
                            onSaveBook = { onSaveBook.invoke(it) }
                        )
                    }
                }

                if (index < books.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: BookEntity,
    onItemSelect: (BookEntity) -> Unit,
    onSaveBook: (BookEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemSelect(book)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = book.title, style = MaterialTheme.typography.headlineSmall)

            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .size(24.dp),
                onClick = {
                    onSaveBook.invoke(book)
                }
            ) {
                Image(
                    painter = painterResource(
                        id = if (book.isSaved)
                            R.drawable.ic_bookmark_active
                        else
                            R.drawable.ic_bookmark
                    ),
                    contentDescription = stringResource(id = R.string.saved_books)
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "Authors: ${book.authors.joinToString { it.name }}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "Subjects: ${book.subjects.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(
    name = "Books Screen Light Mode",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BooksContentScreenPreview() {
    val mockBooks = listOf(
        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
    )

    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
}

@Preview(
    name = "Books Screen Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BooksContentScreenDarkPreview() {
    val mockBooks = listOf(
        BookEntity(1, "Sample Book 1", false, listOf(), listOf()),
        BookEntity(2, "Sample Book 2", true, listOf(), listOf())
    )

    BooksContentScreen(books = mockBooks, onItemSelect = {}, onSaveBook = {})
}

@Preview(
    name = "Books Screen Light Mode",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BookItemScreenPreview() {
    val mockBook =   BookEntity(1, "Sample Book 1", false, listOf(), listOf())

    BookItem(book = mockBook, onItemSelect = {}, onSaveBook = {})
}