package io.ubyte.tldr.search

import io.ubyte.tldr.model.PageItem

sealed class SearchViewState {
    data class SearchResult(val pages: List<PageItem>) : SearchViewState()
    data class RecentPages(val pages: List<PageItem>) : SearchViewState()
    object NoResult : SearchViewState()
}
