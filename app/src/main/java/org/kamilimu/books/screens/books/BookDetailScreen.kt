package org.kamilimu.books.screens.books

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kamilimu.books.shared.components.LottieView

@Composable
fun BookDetail(){
    LottieView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        animationFile = "loading.json"
    )
}