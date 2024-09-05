package org.kamilimu.books.screens.settings

data class SettingsScreenState(
    var example: String = "",
    var exampleBool: Boolean = false,
    var exampleInt: Int = 0,
    var errorMessage: String = ""
)