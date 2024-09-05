package org.kamilimu.books.shared.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

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