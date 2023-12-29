package io.ubyte.tldr.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.ubyte.tldr.R
import io.ubyte.tldr.compose.Pages
import io.ubyte.tldr.compose.Toolbar

@Composable
fun Search(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit
) {
    Column(Modifier.padding(horizontal = 8.dp)) {
        Toolbar { SearchField(viewModel) }
        when (val uiState = viewModel.uiState.collectAsState().value) {
            is SearchViewState.NoResult -> {
                Text(text = stringResource(R.string.no_result))
            }

            is SearchViewState.SearchResult -> {
                Pages(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    pages = uiState.pages,
                    openPageDetails = openPageDetails
                )
            }
        }
    }
}

@Composable
private fun SearchField(viewModel: SearchViewModel) {
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            textStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onBackground),
            cursorBrush = SolidColor(Color.Red),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box {
                    if (textFieldValue.text.isEmpty()) Text(stringResource(R.string.search_field))
                    innerTextField()
                }
            }
        )
        ClearTextButton(textFieldValue.text) { textFieldValue = TextFieldValue() }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }
    LaunchedEffect(textFieldValue) { viewModel.querySearch(textFieldValue.text) }
}

@Composable
private fun ClearTextButton(text: String, onClick: () -> Unit) {
    if (text.isNotEmpty()) {
        IconButton(onClick = { onClick() }) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = stringResource(R.string.clear)
            )
        }
    }
}
