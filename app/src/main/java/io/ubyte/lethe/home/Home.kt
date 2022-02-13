package io.ubyte.lethe.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
fun HomeSearchBar(openSearch: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxSize()
            .clip(CircleShape)
            .background(Color.DarkGray)
            .clickable { openSearch() }
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 16.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
            Text(text = "Search commands")
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
