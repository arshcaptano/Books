package org.kamilimu.books.util.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.kamilimu.books.R
import org.kamilimu.books.ui.theme.BooksTheme
import org.kamilimu.books.viewbooks.domain.model.Person

@Composable
fun <T> BookCardItem(
    items: List<T>,
    itemLabel: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (items.size < 2) "${itemLabel}:" else "${itemLabel}s:",
            style =  MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp,
            modifier = Modifier.alignByBaseline()
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_large)))
        Column(modifier = Modifier.weight(1f)) {
            items.forEach { item ->
                when(item) {
                    is Person -> {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is String -> {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
            }
        }
    }
}


@Preview(name = "BookCardItem Dark", showBackground = true)
@Composable
private fun BookCardItemDarkMode() {
    BooksTheme(darkTheme = true) {
        BookCardItem(
            items =  listOf("History", "Philosophy"),
            itemLabel = "Subject",
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        )
    }
}


@Preview(name = "BookCardItem Light", showBackground = true)
@Composable
private fun BookCardItemLightMode() {
    BooksTheme(darkTheme = false) {
        BookCardItem(
            items =  listOf("History", "Philosophy"),
            itemLabel = "Subject",
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        )
    }
}