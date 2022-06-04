package io.ubyte.tldr.home

import io.ubyte.tldr.model.PageItem

data class HomeViewState(
    val showToolbar: Boolean = false,
    val pages: List<PageItem> = emptyList()
) {
    val showMostFrequent: Boolean
        get() = pages.isNotEmpty()
}
