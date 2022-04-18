package io.ubyte.lethe.home

import io.ubyte.lethe.model.PageItem

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Initial : HomeViewState()
    data class Data(val pages: List<PageItem>) : HomeViewState()
}
