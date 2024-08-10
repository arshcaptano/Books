package org.kamilimu.books.viewbooks.presentation.books_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.kamilimu.books.ui.theme.BooksTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}

@Preview(name = "ErrorScreenLight", showBackground = true)
@Composable
private fun ErrorScreenPreviewLight() {
    BooksTheme(darkTheme = false) {
        ErrorScreen(message = "Something went wrong")
    }
}


@Preview(name = "ErrorScreenDark", showBackground = true)
@Composable
private fun ErrorScreenPreviewDark() {
    BooksTheme(darkTheme = false) {
        ErrorScreen(message = "Something went wrong")
    }
}