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
import io.ubyte.tldr.model.PageItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home(
    viewModel: HomeViewModel,
    openSearch: () -> Unit,
    openPageDetails: (pageId: Long) -> Unit
) {
    viewModel.uiState?.let { state ->
        Column(Modifier.padding(horizontal = 8.dp)) {
            SetupToolbar(state.showToolbar, openSearch)
            if (state.showMostFrequent) {
                Spacer(Modifier.height(32.dp))
                FrequentPagesCard(state.pages, openPageDetails)
            } else {
                DisplayAppName()
            }
        }
    }
}

@Composable
private fun FrequentPagesCard(
    pages: List<PageItem>,
    openPageDetails: (pageId: Long) -> Unit
) {
    Column {
        SectionHeader(stringResource(R.string.frequent_pages))
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Pages(
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
                pages = pages,
                openPageDetails = openPageDetails
            )
        }
    }
}

@Composable
private fun SetupToolbar(showToolbar: Boolean, openSearch: () -> Unit) {
    Crossfade(showToolbar) { toolbar ->
        if (toolbar) {
            Toolbar(Modifier.clickable { openSearch() }) {
                Text(stringResource(R.string.search_field))
            }
        } else {
            Spacer(Modifier.height(Toolbar.height))
        }
    }
}

@Composable
private fun DisplayAppName() {
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
                TextCursor(style)
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
private fun TextCursor(
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
