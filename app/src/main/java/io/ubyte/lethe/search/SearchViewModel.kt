package io.ubyte.lethe.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.model.PageIdentifier
import io.ubyte.lethe.store.PageStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val store: PageStore
) : ViewModel() {
    var uiState by mutableStateOf(SearchViewState())
        private set

    fun querySearch(term: String) {
        if (term.isNotEmpty()) {
            viewModelScope.launch {
                store.queryPages(term).collect { pages ->
                    uiState = uiState.copy(searchResult = pages)
                }
            }
        } else {
            uiState = uiState.copy(searchResult = emptyList())
        }
    }

    init {
        viewModelScope.launch {
            store.queryMostRecent().collect { pages ->
                uiState = uiState.copy(recentPages = pages)
            }
        }
    }
}
