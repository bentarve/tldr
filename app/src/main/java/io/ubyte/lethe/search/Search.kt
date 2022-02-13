package io.ubyte.lethe.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.ubyte.lethe.core.ui.components.BackButton
import io.ubyte.lethe.core.ui.components.Toolbar

@Composable
fun Search(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Column {
            Toolbar(navigateUp) {
                val focusRequester = remember { FocusRequester() }
                SearchField(
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .focusRequester(focusRequester)
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
            Divider(thickness = 2.dp)
            MostRecent(viewModel, openPageDetails)
        }
    }
}

@Composable
fun SearchField(modifier: Modifier) {
    var value by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            singleLine = true,
            modifier = modifier.weight(2f),
            decorationBox = { innerTextField ->
                Box {
                    if (value.text.isEmpty()) {
                        Text("Search commands")
                    }
                    innerTextField()
                }
            }
        )
        if (value.text.isNotEmpty()) {
            IconButton(
                onClick = { value = TextFieldValue("") },
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
}

@Composable
private fun MostRecent(
    viewModel: SearchViewModel,
    openPageDetails: (Long) -> Unit
) {
    val pages = viewModel.mostRecentPages
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Most recent",
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
        )
        pages.forEach { page ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openPageDetails(page.id) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = 24.dp),
                    imageVector = Icons.Default.History, // todo extract
                    contentDescription = "Recent"
                )
                Column {
                    Text(text = page.name)
                    Text(text = page.platform)
                }
            }
        }
    }
}
