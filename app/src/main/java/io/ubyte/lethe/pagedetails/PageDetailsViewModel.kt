package io.ubyte.lethe.pagedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.Destinations.PAGE_ID
import io.ubyte.lethe.model.Page
import io.ubyte.lethe.store.PageStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageDetailsViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val store: PageStore
) : ViewModel() {
    private var page by mutableStateOf(Page("", "", ""))

    val pageName: String
        get() = page.name

    val pageContent: String
        get() = page.markdown.substringAfter("\n")

    init {
        viewModelScope.launch {
            page = store.queryPage(checkNotNull(savedState.get(PAGE_ID)))
        }
    }
}
