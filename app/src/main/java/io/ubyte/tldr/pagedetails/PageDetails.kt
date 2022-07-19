package io.ubyte.tldr.pagedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = viewModel.uiState.pageName) },
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
        viewModel.uiState.pageContent?.let { markdown ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(markdown)
            }
        }
    }

    LocalSoftwareKeyboardController.current?.hide()
}
