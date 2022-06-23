package io.ubyte.tldr.pagedetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.ubyte.tldr.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PageDetails(
    viewModel: PageDetailsViewModel,
    navigateUp: () -> Unit
) {
    with(viewModel.uiState) {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    title = { Text(text = name) },
                    navigationIcon = {
                        IconButton(onClick = { navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.navigate_back)
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            content?.let { annotatedString ->
                Text(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp),
                    text = annotatedString
                )
            }
        }
    }

    LocalSoftwareKeyboardController.current?.hide()
}
