package io.ubyte.tldr.search

import io.ubyte.tldr.model.PageItem

sealed interface SearchViewState {
    data class SearchResult(val pages: List<PageItem>) : SearchViewState
    object NoResult : SearchViewState
}
