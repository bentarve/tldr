package io.ubyte.lethe.home.allpages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.ubyte.lethe.home.topbar.HomeTopBar
import io.ubyte.lethe.home.topbar.HomeTopBar.TopBarHeight

@Composable
fun AllPages(
    openSearch: () -> Unit,
    openPageDetails: (Long) -> Unit
) {
    val toolbarHeightPx = with(LocalDensity.current) { TopBarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        AllPagesColumn(hiltViewModel(), openPageDetails)
        HomeTopBar(openSearch, toolbarOffsetHeightPx)
    }
}

@Composable
private fun AllPagesColumn(
    viewModel: AllPagesViewModel,
    openPageDetails: (pageId: Long) -> Unit
) {
    val pages = viewModel.pager.collectAsLazyPagingItems()
    LazyColumn(contentPadding = PaddingValues(top = TopBarHeight)) {
        items(pages) { page ->
            page?.let {
                Text(
                    text = "${page.name} - ${page.platform}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clickable { openPageDetails(page.id) }
                )
            }
        }
    }
}
