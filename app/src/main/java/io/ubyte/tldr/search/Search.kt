package io.ubyte.tldr.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.ubyte.tldr.compose.Pages
import io.ubyte.tldr.compose.Toolbar

@Composable
fun Search(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit
) {
    Column(Modifier.padding(horizontal = 8.dp)) {
        Toolbar {
            SearchField(viewModel)
        }
        when (val result = viewModel.uiState) {
            is SearchViewState.NoResult -> {
                Text(text = "No result")
            }
            is SearchViewState.RecentPages -> {
                Pages(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    pages = result.pages,
                    openPageDetails = openPageDetails
                )
            }
            is SearchViewState.SearchResult -> {
                Pages(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    pages = result.pages,
                    openPageDetails = openPageDetails
                )
            }
        }
    }
}

@Composable
fun SearchField(
    viewModel: SearchViewModel
) {
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            singleLine = true,
            cursorBrush = SolidColor(Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            decorationBox = { innerTextField ->
                Box {
                    if (textFieldValue.text.isEmpty()) {
                        Text(
                            text = "Search or type page", // todo resources
                        )
                    }
                    innerTextField()
                }
            }
        )
        if (textFieldValue.text.isNotEmpty()) {
            IconButton(
                onClick = { textFieldValue = TextFieldValue() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear, // todo extract
                    contentDescription = "Clear"
                )
            }
        }
    }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
    LaunchedEffect(textFieldValue) {
        viewModel.querySearch(textFieldValue.text)
    }
}
