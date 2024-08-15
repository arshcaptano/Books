package org.kamilimu.books.screens.bookmarks

import org.kamilimu.books.screens.bookmarks.models.Bookmark

data class BookmarksScreenState(
    var isLoading: Boolean = true,
    var bookmarks: List<Bookmark> = emptyList(),
    var errorMessage: String = ""
)
