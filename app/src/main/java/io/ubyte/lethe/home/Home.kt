package io.ubyte.lethe.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.ubyte.lethe.core.ui.components.TopBar
import io.ubyte.lethe.model.PageIdentifier
import io.ubyte.lethe.pages.Pages

@Composable
fun Home(
    viewModel: HomeViewModel,
    openSearch: () -> Unit,
    openPageDetails: (pageId: Long) -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        when (viewModel.uiState) {
            is HomeViewState.Loading -> Loading()
            is HomeViewState.Empty -> {
                Pages(
                    viewModel = hiltViewModel(),
                    openSearch = openSearch,
                    openPageDetails = openPageDetails
                )
            }
            is HomeViewState.Data -> {
                MostFrequent(
                    (viewModel.uiState as HomeViewState.Data).pages,
                    openSearch,
                    openPageDetails
                )
            }
        }
    }
}

@Composable
private fun MostFrequent(
    pages: List<PageIdentifier>,
    openSearch: () -> Unit,
    openPageDetails: (pageId: Long) -> Unit
) {
    Column {
        TopBar { SearchButton(openSearch) }
        RecurringPages(
            modifier = Modifier.padding(top = 2.dp),
            pages = pages,
            contentDescription = "Frequent",
            icon = Icons.Default.Code, // todo extract
            openPageDetails = openPageDetails
        )
    }
}

@Composable
fun RecurringPages(
    modifier: Modifier = Modifier,
    pages: List<PageIdentifier>,
    contentDescription: String,
    icon: ImageVector,
    openPageDetails: (pageId: Long) -> Unit
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "$contentDescription commands",
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
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
                    imageVector = icon,
                    contentDescription = contentDescription
                )
                Column {
                    Text(text = page.name)
                    Text(text = page.platform)
                }
            }
        }
    }
}

@Composable
fun SearchButton(openSearch: () -> Unit) {
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
