package io.ubyte.lethe.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ubyte.lethe.home.allpages.AllPages

@Composable
fun Home(
    viewModel: HomeViewModel,
    openSearch: () -> Unit,
    openPageDetails: (pageId: Long) -> Unit
) {
    when (viewModel.uiState) {
        Home.ALL_PAGES -> {
            AllPages(openSearch, openPageDetails)
        }
        Home.MOST_FREQUENT -> {
        }
        Home.LOADING -> {
            Loading()
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

enum class Home {
    ALL_PAGES,
    MOST_FREQUENT,
    LOADING,
    INITIAL
}
