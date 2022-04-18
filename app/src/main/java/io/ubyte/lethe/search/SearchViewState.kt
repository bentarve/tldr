package io.ubyte.lethe.search

import io.ubyte.lethe.model.PageItem

sealed class SearchViewState {
    data class SearchResult(val pages: List<PageItem>) : SearchViewState()
    data class RecentPages(val pages: List<PageItem>) : SearchViewState()
    object NoResult : SearchViewState()
}
