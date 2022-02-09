package io.ubyte.lethe.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.usecases.UpdatePages
import io.ubyte.lethe.store.PageStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: PageStore,
    private val updatePages: UpdatePages
) : ViewModel() {
    var uiState by mutableStateOf(Home.INITIAL)
        private set

    init {
        viewModelScope.launch {
            if (store.count() == 0L) {
                uiState = Home.LOADING
                updatePages()
                uiState = Home.ALL_PAGES
            } else {
                uiState = Home.ALL_PAGES
            }
        }
    }
}
