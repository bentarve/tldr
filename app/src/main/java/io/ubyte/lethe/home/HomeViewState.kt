package io.ubyte.lethe.home

import io.ubyte.lethe.model.PageIdentifier

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Empty : HomeViewState()
    data class Data(val pages: List<PageIdentifier>) : HomeViewState()
}
