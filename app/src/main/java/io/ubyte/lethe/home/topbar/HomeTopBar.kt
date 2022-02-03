package io.ubyte.lethe.home.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.ubyte.lethe.home.topbar.HomeTopBar.TopBarHeight
import kotlin.math.roundToInt

@Composable
fun HomeTopBar(
    openSearch: () -> Unit,
    toolbarOffsetHeightPx: MutableState<Float>
) {
    Surface(
        modifier = Modifier
            .height(TopBarHeight)
            .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
    ) {
        SearchBar(openSearch)
    }
}

@Composable
fun HomeTopBar(openSearch: () -> Unit) {
    Surface(
        modifier = Modifier.height(TopBarHeight)
    ) {
        SearchBar(openSearch)
    }
}

@Composable
private fun SearchBar(openSearch: () -> Unit) {
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

object HomeTopBar {
    val TopBarHeight = 56.dp
}
