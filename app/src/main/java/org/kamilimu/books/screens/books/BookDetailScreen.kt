package org.kamilimu.books.screens.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.kamilimu.books.screens.books.models.Book
import org.kamilimu.books.shared.components.LottieView

@Composable
fun BookDetailScreen(book: Book){
    Box {
        Text(
            book.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        LottieView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            animationFile = "loading.json"
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BookDetailScreenPreview() {
    BookDetailScreen(book = Book())
}