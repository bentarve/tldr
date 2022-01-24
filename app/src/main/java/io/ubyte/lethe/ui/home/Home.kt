package io.ubyte.lethe.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Home(
    onSearchClick: () -> Unit,
    onPageClick: (String) -> Unit
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
        HomeContent(onPageClick)
        HomeTopBar(onSearchClick, toolbarOffsetHeightPx)
    }

}

@Composable
private fun HomeContent(onPageClick: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(top = TopBarHeight)) {
        items(100) { index ->
            Text(
                text = "Manual page, $index",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable { onPageClick(index.toString()) }
            )
        }
    }
}

@Composable
private fun HomeTopBar(onSearchClick: () -> Unit, toolbarOffsetHeightPx: MutableState<Float>) {
    Surface(
        modifier = Modifier
            .height(TopBarHeight)
            .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
    ) {
        TopBarContent(onSearchClick)
    }
}

@Composable
private fun TopBarContent(onSearchClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxSize()
            .clip(CircleShape)
            .background(Color.DarkGray)
            .clickable { onSearchClick() }
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

private val TopBarHeight = 56.dp
