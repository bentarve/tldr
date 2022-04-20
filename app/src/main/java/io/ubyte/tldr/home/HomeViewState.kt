package io.ubyte.tldr.home

import io.ubyte.tldr.model.PageItem

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Initial : HomeViewState()
    data class Data(val pages: List<PageItem>) : HomeViewState()
}
