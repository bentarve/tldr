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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val store: PageStore
) : ViewModel() {
    var mostRecentPages by mutableStateOf(emptyList<PageIdentifier>())
        private set

    fun querySearch(term: String) {
        viewModelScope.launch {
            //store.queryPages(term)
        }
    }

    init {
        viewModelScope.launch {
            store.queryMostRecent().collect { pages ->
                mostRecentPages = pages
            }
        }
    }
}
