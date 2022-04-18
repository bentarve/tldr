package io.ubyte.lethe.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.model.Icon
import io.ubyte.lethe.model.PageIdentifier
import io.ubyte.lethe.model.PageItem
import io.ubyte.lethe.search.SearchViewState.*
import io.ubyte.lethe.store.PageStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val store: PageStore
) : ViewModel() {
    private var recentPages by mutableStateOf<List<PageItem>>(emptyList())

    var uiState by mutableStateOf<SearchViewState>(RecentPages(emptyList()))
        private set

    fun querySearch(term: String) {
        if (term.isNotEmpty()) {
            viewModelScope.launch {
                store.queryPages(term).collect { pages ->
                    uiState = if (pages.isNotEmpty()) {
                        mergeResults(pages)
                    } else {
                        NoResult
                    }
                }
            }
        } else {
            uiState = SearchResult(recentPages)
        }
    }

    // Merges query pages and recent pages to one list
    private fun mergeResults(pages: List<PageIdentifier>): SearchResult {
        val searchResult = pages.map { page ->
            PageItem(page, Icon.SEARCH_RESULT)
        }
        val diff = 10 - searchResult.size.coerceAtMost(10)
        return SearchResult(searchResult + recentPages.take(diff))
    }

    init {
        viewModelScope.launch {
            store.queryMostRecent().collect { pages ->
                recentPages = pages.map { page ->
                    PageItem(page, Icon.RECENT_PAGES)
                }.also {
                    uiState = RecentPages(it)
                }
            }
        }
    }
}
