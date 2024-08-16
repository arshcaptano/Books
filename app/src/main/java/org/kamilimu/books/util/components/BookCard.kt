package org.kamilimu.books.util.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.kamilimu.books.R
import org.kamilimu.books.ui.theme.BooksTheme
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.Person

@Composable
fun BookCard(
    book: Book,
    navController: NavHostController,
    onFavouriteClicked: () -> Unit,
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BookCardContent(
        title = book.title,
        subjects = book.subjects,
        authors = book.authors,
        onCardClicked = { /*TODO*/ },
        isBookmarked = book.isBookmarked,
        onFavouriteClicked = onFavouriteClicked,
        modifier = modifier
    )
}


@Composable
fun BookCardContent(
    title: String,
    subjects: List<String>,
    authors: List<Person>,
    onCardClicked: () -> Unit,
    isBookmarked: Boolean,
    onFavouriteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
            .clickable(
                onClickLabel = "View Details",
                onClick = onCardClicked
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
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
            HorizontalDivider(
                thickness = dimensionResource(R.dimen.divider_thickness),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            BookCardItem(
                items = authors,
                itemLabel = "Author",
                modifier = Modifier
                    .fillMaxSize()
            )
            BookCardItem(
                items = subjects,
                itemLabel = "Subject",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}


@Preview(name = "BookCardContentLight", showBackground = true)
@Composable
private fun BookCardContentPreviewLight() {
    BooksTheme(darkTheme = false) {
        BookCardContent(
            title = "The Alchemist",
            subjects = listOf("History", "Philosophy"),
            authors = listOf(Person(name = "Paulo Coelho", birthYear = 1950, deathYear = null)),
            onCardClicked = {},
            isBookmarked = true,
            onFavouriteClicked = {}
        )
    }
}


@Preview(name = "BookCardContentDark", showBackground = true)
@Composable
private fun BookCardContentPreviewDark() {
    BooksTheme(darkTheme = true) {
        BookCardContent(
            title = "The Alchemist",
            subjects = listOf("History", "Philosophy"),
            authors = listOf(Person(name = "Paulo Coelho", birthYear = 1950, deathYear = null)),
            onCardClicked = {},
            isBookmarked = false,
            onFavouriteClicked = {}
        )
    }
}