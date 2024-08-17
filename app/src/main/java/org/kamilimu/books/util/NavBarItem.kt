package org.kamilimu.books.util

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val label: String,
    val destination: ScreenNames,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector
)