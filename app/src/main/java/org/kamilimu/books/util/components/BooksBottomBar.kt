package org.kamilimu.books.util.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.kamilimu.books.R
import org.kamilimu.books.ui.theme.BooksTheme
import org.kamilimu.books.util.NavBarItem
import org.kamilimu.books.util.ScreenNames

@Composable
fun BooksBottomBar(
    modifier: Modifier = Modifier,
    currentScreen: ScreenNames,
    onHomeClicked: () -> Unit,
    onFavoritesClicked: () -> Unit
) {
    val navBarItems = listOf(
        NavBarItem(
            label = stringResource(R.string.nav_bar_home),
            destination = ScreenNames.HomeScreen,
            outlinedIcon = Icons.Outlined.Home,
            filledIcon = Icons.Filled.Home
        ),
        NavBarItem(
            label = stringResource(R.string.nav_bar_favourites),
            destination = ScreenNames.FavouritesScreen,
            outlinedIcon = Icons.Outlined.FavoriteBorder,
            filledIcon = Icons.Filled.Favorite
        )
    )

    NavigationBar(modifier = modifier) {
        navBarItems.forEach { item ->
            NavigationBarItem(
                selected = currentScreen == item.destination,
                label = {
                    Text(text = item.label)
                },
                icon = {
                    BadgedBox(badge = {}) {
                        Icon(
                            imageVector = if (currentScreen == item.destination) item.filledIcon else item.outlinedIcon,
                            contentDescription = item.destination.title
                        )
                    }
                },
                onClick = if (item.destination == ScreenNames.HomeScreen) onHomeClicked else onFavoritesClicked
            )
        }
    }
}


@Preview(name = "BottomBar Light", showBackground = true)
@Composable
private fun BooksBottomBarLightPreview() {
    BooksTheme(darkTheme = false) {
        BooksBottomBar(
            currentScreen = ScreenNames.HomeScreen,
            onHomeClicked = {},
            onFavoritesClicked = {}
        )
    }
}


@Preview(name = "BottomBar Dark", showBackground = true)
@Composable
private fun BooksBottomBarDarkPreview() {
    BooksTheme(darkTheme = true) {
        BooksBottomBar(
            currentScreen = ScreenNames.FavouritesScreen,
            onHomeClicked = {},
            onFavoritesClicked = {}
        )
    }
}