package io.ubyte.lethe.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.ubyte.lethe.core.ui.components.TopBar
import io.ubyte.lethe.home.RecurringPages

@Composable
fun Search(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Column {
            TopBar(navigateUp = navigateUp) {
                val focusRequester = remember { FocusRequester() }
                SearchField(
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .focusRequester(focusRequester),
                )
                LaunchedEffect(Unit) { focusRequester.requestFocus() }
            }
            Divider(thickness = 2.dp)
            if (viewModel.uiState.searchResult.isNotEmpty()) {
                SearchResult(viewModel, openPageDetails)
            } else {
                MostRecent(viewModel, openPageDetails)
            }
        }
    }
}
@Composable
private fun SearchField(
    viewModel: SearchViewModel,
    modifier: Modifier
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            singleLine = true,
            modifier = modifier.weight(2f),
            decorationBox = { innerTextField ->
                Box {
                    if (textFieldValue.text.isEmpty()) {
                        Text("Search commands")
                    }
                    innerTextField()
                }
            }
        )
        if (textFieldValue.text.isNotEmpty()) {
            IconButton(
                onClick = { textFieldValue = TextFieldValue() },
                Modifier.requiredWidth(IntrinsicSize.Max)
            ) {
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear"
                )
            }
        }
    }
    LaunchedEffect(textFieldValue) {
        viewModel.querySearch(textFieldValue.text)
    }
}

@Composable
private fun SearchResult(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit
) {
    RecurringPages(
        pages = viewModel.uiState.searchResult,
        contentDescription = "Search",
        icon = Icons.Default.Search,
        openPageDetails = openPageDetails
    )
}

@Composable
private fun MostRecent(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit
) {
    RecurringPages(
        pages = viewModel.uiState.recentPages,
        contentDescription = "Recent",
        icon = Icons.Default.History, // todo extract
        openPageDetails = openPageDetails
    )
}
