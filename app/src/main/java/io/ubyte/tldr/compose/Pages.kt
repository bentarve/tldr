package io.ubyte.tldr.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.ubyte.tldr.model.PageItem

@Composable
fun Pages(
    modifier: Modifier = Modifier,
    pages: List<PageItem>,
    openPageDetails: (Long) -> Unit
) {
    Column(modifier) {
        pages.forEach { page ->
            ListItem(page, openPageDetails)
        }
    }
}
