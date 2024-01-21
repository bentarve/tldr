package io.ubyte.tldr.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.tldr.model.Icon
import io.ubyte.tldr.model.PageIdentifier
import io.ubyte.tldr.model.PageItem
import io.ubyte.tldr.search.SearchViewState.NoResult
import io.ubyte.tldr.search.SearchViewState.SearchResult
import io.ubyte.tldr.store.PageStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val store: PageStore
) : ViewModel() {
    private val recentPages = MutableStateFlow<List<PageItem>>(emptyList())

    private val _uiState = MutableStateFlow<SearchViewState>(SearchResult(emptyList()))
    val uiState = _uiState.asStateFlow()

    fun querySearch(term: String) = viewModelScope.launch {
        if (term.isEmpty()) {
            _uiState.update { SearchResult(recentPages.value) }
        } else {
            store.queryPages(term).collect { queryPages ->
                if (queryPages.isEmpty()) {
                    _uiState.update { NoResult }
                } else {
                    _uiState.update { SearchResult(queryPages mergeWith recentPages.value) }
                }
            }
        }
    }

    // Merge query pages and recent pages to one list
    private infix fun List<PageIdentifier>.mergeWith(recentPages: List<PageItem>): List<PageItem> {
        val searchResult = this.map { page -> PageItem(page, Icon.SearchResult) }
        val diff = MAX_LIST_ITEMS - searchResult.size.coerceAtMost(MAX_LIST_ITEMS)
        return searchResult + recentPages.take(diff)
    }

    init {
        viewModelScope.launch {
            store.queryMostRecent().collect { pages ->
                recentPages.update {
                    pages.map { page ->
                        PageItem(page, Icon.RecentPage)
                    }
                }
                _uiState.update { SearchResult(recentPages.value) }
            }
        }
    }
}

private const val MAX_LIST_ITEMS = 8
