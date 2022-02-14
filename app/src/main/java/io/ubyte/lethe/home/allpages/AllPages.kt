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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.ubyte.lethe.core.ui.components.TopBar
import io.ubyte.lethe.core.ui.components.TopBarHeight
import io.ubyte.lethe.home.HomeSearchBar
import io.ubyte.lethe.home.HomeViewModel
import kotlin.math.roundToInt

@Composable
fun AllPages(
    viewModel: HomeViewModel,
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
        ListOfAllPages(viewModel, openPageDetails)
        TopBar(
            modifier = Modifier.offset {
                IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt())
            }
        ) {
            HomeSearchBar(openSearch)
        }
    }
}

@Composable
private fun ListOfAllPages(
    viewModel: HomeViewModel,
    openPageDetails: (pageId: Long) -> Unit
) {
    val pages = viewModel.pager.collectAsLazyPagingItems()
    LazyColumn(
        contentPadding = PaddingValues(
            top = TopBarHeight + 16.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pages) { page ->
            page?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { openPageDetails(page.id) }
                ) {
                    Text(text = page.name)
                    Text(text = page.platform)
                }
            }
        }
    }
}
