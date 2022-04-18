package io.ubyte.lethe.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.home.HomeViewState.*
import io.ubyte.lethe.model.Icon
import io.ubyte.lethe.model.PageItem
import io.ubyte.lethe.store.PageStore
import io.ubyte.lethe.usecases.UpdatePages
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: PageStore,
    private val updatePages: UpdatePages
) : ViewModel() {
    var uiState by mutableStateOf<HomeViewState>(Loading)
        private set

    private fun loadPages() {
        viewModelScope.launch {
            if (store.count() == 0L) {
                updatePages()
                uiState = Initial
            } else {
                store.queryMostFrequent().collect { frequentPages ->
                    uiState = if (frequentPages.size > 3) {
                        val pages = frequentPages.map { page ->
                            PageItem(page, Icon.FREQUENT_PAGES)
                        }
                        Data(pages)
                    } else {
                        Initial
                    }
                }
            }
        }
    }

    init {
        loadPages()
    }
}
