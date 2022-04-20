package io.ubyte.tldr.pagedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.tldr.Destinations.PAGE_ID
import io.ubyte.tldr.model.Page
import io.ubyte.tldr.store.PageStore
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
            page = store.queryPage(checkNotNull(savedState[PAGE_ID]))
        }
    }
}
