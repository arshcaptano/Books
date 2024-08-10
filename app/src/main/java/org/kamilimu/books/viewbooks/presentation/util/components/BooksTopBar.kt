package org.kamilimu.books.viewbooks.presentation.util.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.kamilimu.books.ui.theme.BooksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksTopBar(
    title: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier
    )
}


@Preview(name = "BooksTopBarDark", showBackground = true)
@Composable
private fun BooksTopBarDarkPreview() {
    BooksTheme(darkTheme = true) {
        BooksTopBar(title = "Books")
    }
}


@Preview(name = "BooksTopBarLight", showBackground = true)
@Composable
private fun BooksTopBarLightPreview() {
    BooksTheme(darkTheme = false) {
        BooksTopBar(title = "Books")
    }
}