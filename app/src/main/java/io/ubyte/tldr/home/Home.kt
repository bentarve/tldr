package io.ubyte.tldr.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.ubyte.tldr.R
import io.ubyte.tldr.compose.Pages
import io.ubyte.tldr.compose.SectionHeader
import io.ubyte.tldr.compose.Toolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home(
    viewModel: HomeViewModel,
    openSearch: () -> Unit,
    openPageDetails: (pageId: Long) -> Unit
) {
    Column(Modifier.padding(horizontal = 8.dp)) {
        val uiState = viewModel.uiState
        Crossfade(uiState) { state ->
            SetupToolbar(state, openSearch)
        }
        if (uiState is HomeViewState.Data) {
            Spacer(Modifier.height(32.dp))
            Column {
                SectionHeader(stringResource(R.string.frequent_pages))
                Spacer(Modifier.height(16.dp))
                Card(Modifier.fillMaxWidth()) {
                    Pages(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        pages = uiState.pages,
                        openPageDetails = openPageDetails
                    )
                }
            }
        } else {
            Tldr()
        }
    }
}

@Composable
private fun SetupToolbar(state: HomeViewState, openSearch: () -> Unit) {
    if (state == HomeViewState.Loading) {
        Spacer(Modifier.height(Toolbar.height))
    } else {
        Toolbar(Modifier.clickable { openSearch() }) {
            Text(stringResource(R.string.search_field))
        }
    }
}

@Composable
private fun Tldr() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = BiasAlignment(0f, -0.4f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                val style = MaterialTheme.typography.h1
                Text(
                    text = stringResource(R.string.app_name),
                    style = style
                )
                BlinkingCursor(style)
            }
            Spacer(Modifier.padding(16.dp))
            Text(
                text = stringResource(R.string.app_description),
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
private fun BlinkingCursor(
    textStyle: TextStyle
) {
    var cursor by remember { mutableStateOf(" ") }
    Text(text = cursor, style = textStyle)
    LaunchedEffect(Unit) {
        launch {
            while (true) {
                delay(700)
                cursor = if (cursor == " ") "_" else " "
            }
        }
    }
}
