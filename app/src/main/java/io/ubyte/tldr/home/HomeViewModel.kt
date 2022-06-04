package io.ubyte.tldr.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.tldr.model.Icon
import io.ubyte.tldr.model.PageItem
import io.ubyte.tldr.store.PageStore
import io.ubyte.tldr.sync.PageSynchronizer
import io.ubyte.tldr.sync.PageSynchronizer.SyncState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: PageStore,
    private val synchronizer: PageSynchronizer
) : ViewModel() {
    var uiState by mutableStateOf<HomeViewState?>(null)
        private set

    private suspend fun uiState(showToolbar: Boolean = true) {
        store.queryMostFrequent().collectLatest { frequentPages ->
            uiState = if (frequentPages.size > 3) {
                val pages = frequentPages.map { page ->
                    PageItem(page, Icon.FREQUENT_PAGE)
                }
                HomeViewState(showToolbar, pages)
            } else {
                HomeViewState(showToolbar, emptyList())
            }
        }
    }

    private fun collectSyncState() = viewModelScope.launch {
        synchronizer.state.collectLatest { state ->
            state?.let {
                if (state == SyncState.INITIAL_SYNC) {
                    uiState(showToolbar = false)
                } else {
                    uiState()
                }
            }
        }
    }

    init {
        collectSyncState()
        viewModelScope.launch {
            synchronizer.sync()
        }
    }
}
