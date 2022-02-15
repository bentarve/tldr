package io.ubyte.lethe.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.home.HomeViewState.*
import io.ubyte.lethe.store.PageStore
import io.ubyte.lethe.usecases.UpdatePages
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: PageStore,
    private val updatePages: UpdatePages
) : ViewModel() {
    var uiState by mutableStateOf<HomeViewState>(Data(emptyList()))
        private set

    private fun loadPages() {
        viewModelScope.launch {
            if (store.count() == 0L) {
                uiState = Loading
                updatePages()
                uiState = Empty
            } else {
                store.queryMostFrequent().collect { pages ->
                    uiState = if (pages.size > 3) {
                        Data(pages)
                    } else {
                        Empty
                    }
                }
            }
        }
    }

    init {
        loadPages()
    }
}
