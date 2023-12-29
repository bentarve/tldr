package io.ubyte.tldr.pagedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is PageDetailsViewState.PageDetails -> { pageContent(uiState, navigateUp) }
        is PageDetailsViewState.QueryError -> { pageError(uiState, navigateUp) }
    }

    LocalSoftwareKeyboardController.current?.hide()
}

@Composable
private fun pageContent(
    uiState: PageDetailsViewState.PageDetails,
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = uiState.pageName) },
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
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(uiState.pageContent)
        }
    }
}

@Composable
private fun pageError(
    uiState: PageDetailsViewState.QueryError,
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = "") },
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
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = uiState.message)
        }
    }
}
