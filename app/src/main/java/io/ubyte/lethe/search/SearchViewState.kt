package io.ubyte.lethe.search

import io.ubyte.lethe.model.PageIdentifier


data class SearchViewState(
    val searchResult: List<PageIdentifier> = emptyList(),
    val recentPages: List<PageIdentifier> = emptyList()
)
