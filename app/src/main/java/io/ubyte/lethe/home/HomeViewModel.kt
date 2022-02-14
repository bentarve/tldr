package io.ubyte.lethe.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.model.PageIdentifier
import io.ubyte.lethe.usecases.UpdatePages
import io.ubyte.lethe.store.PageStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: PageStore,
    private val updatePages: UpdatePages
) : ViewModel() {
    var uiState by mutableStateOf(Home.INITIAL)
        private set

    val pager = Pager(PagingConfig(pageSize = 100)) {
        store.queryPagingSource()
    }.flow

    var mostFrequentPages by mutableStateOf(emptyList<PageIdentifier>())
        private set

    private fun loadPages() {
        viewModelScope.launch {
            if (store.count() == 0L) {
                uiState = Home.LOADING
                updatePages()
                uiState = Home.ALL_PAGES
            } else {
                store.queryMostFrequent().collect { pages ->
                    if (pages.size > 3) {
                        mostFrequentPages = pages
                        uiState = Home.MOST_FREQUENT
                    } else {
                        uiState = Home.ALL_PAGES
                    }
                }
            }
        }
    }

    init {
        loadPages()
    }
}
