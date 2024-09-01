package org.kamilimu.books.bookmarks.presentation.bookmarkScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import org.kamilimu.books.R
import org.kamilimu.books.ui.theme.BooksTheme
import org.kamilimu.books.util.components.BookCardItem
import org.kamilimu.books.util.components.DetailsScreenAppBar
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.Person

@Composable
fun BookmarkDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    book: Book,
    onFavouriteClicked: (Int) -> Unit
) {
    Scaffold(
        topBar = { DetailsScreenAppBar(navController = navController) },
        modifier = modifier
    ) { innerPadding ->
        BookDetailsContent(
            title = book.title,
            subjects = book.subjects,
            authors = book.authors,
            translators = book.translators,
            bookshelves = book.bookshelves,
            languages = book.languages,
            mediaType = book.mediaType,
            image = book.formats.jpeg,
            downloadCount = book.downloadCount,
            isBookmarked = book.isBookmarked,
            onFavouriteClicked = { onFavouriteClicked(book.id) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BookDetailsContent(
    modifier: Modifier = Modifier,
    title: String,
    subjects: List<String>,
    authors: List<Person>,
    translators: List<Person>,
    bookshelves: List<String>,
    languages: List<String>,
    mediaType: String,
    image: String,
    downloadCount: Int,
    isBookmarked: Boolean,
    onFavouriteClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .wrapContentSize()
                        .size(300.dp)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onFavouriteClicked
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            HorizontalDivider(
                thickness = dimensionResource(R.dimen.divider_thickness),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            BookCardItem(
                items = authors,
                itemLabel = "Author",
                modifier = Modifier.fillMaxWidth()
            )
            BookCardItem(
                items = subjects,
                itemLabel = "Subject",
                modifier = Modifier.fillMaxWidth()
            )
            BookCardItem(
                items = translators,
                itemLabel = "Translator",
                modifier = Modifier.fillMaxWidth()
            )
            BookCardItem(
                items = bookshelves,
                itemLabel = "Bookshelf",
                modifier = Modifier.fillMaxWidth()
            )
            BookCardItem(
                items = languages,
                itemLabel = "Language",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "BookDetailsDark", showBackground = true)
@Composable
private fun BookDetailsContentPreviewDark() {
    BooksTheme(darkTheme = true) {
        BookDetailsContent(
            title = "Title",
            subjects = emptyList(),
            authors = emptyList(),
            translators = emptyList(),
            bookshelves = emptyList(),
            languages = emptyList(),
            mediaType = "MediaType",
            image = "https://www.gutenberg.org/cache/epub/6593/pg6593.cover.medium.jpg",
            downloadCount = 2,
            isBookmarked = true,
            onFavouriteClicked = {}
        )
    }
}

@Preview(name = "BookDetailsLight", showBackground = true)
@Composable
private fun BookDetailsContentPreviewLight() {
    BooksTheme(darkTheme = false) {
        BookDetailsContent(
            title = "Title",
            subjects = emptyList(),
            authors = emptyList(),
            translators = emptyList(),
            bookshelves = emptyList(),
            languages = emptyList(),
            mediaType = "MediaType",
            image = "https://www.gutenberg.org/cache/epub/1513/pg1513.cover.medium.jpg",
            downloadCount = 2,
            isBookmarked = true,
            onFavouriteClicked = {},
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}