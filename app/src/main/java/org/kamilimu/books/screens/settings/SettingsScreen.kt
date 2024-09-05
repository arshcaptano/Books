package org.kamilimu.books.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kamilimu.books.screens.books.BooksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen() {
    val vm: SettingsViewModel = koinViewModel()
    val screenState = vm.screenState.collectAsState()
    val exampleText = screenState.value.example
    val exampleBool = screenState.value.exampleBool
    val exampleInt = screenState.value.exampleInt

    LaunchedEffect(Unit) {
        vm.saveTest()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Settings"
        )

        Text(
            modifier = Modifier
                .padding(8.dp),
            style = TextStyle(fontSize = 24.sp),
            text = "Saved String Value: $exampleText"
        )

        Text(
            modifier = Modifier
                .padding(8.dp),
            style = TextStyle(fontSize = 24.sp),
            text = "Saved Boolean Value: $exampleBool"
        )

        Text(
            modifier = Modifier
                .padding(8.dp),
            style = TextStyle(fontSize = 24.sp),
            text = "Saved Int Value: $exampleInt"
        )

    }
}