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
import io.ubyte.tldr.usecases.MarkdownParser
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class PageDetailsViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val store: PageStore,
    private val parser: MarkdownParser
) : ViewModel() {
    var uiState by mutableStateOf(PageDetailsViewState())
        private set

    private suspend fun queryPage(): Page? = try {
        store.queryPage(checkNotNull(savedState[PAGE_ID]))
    } catch (e: Exception) {
        logcat(LogPriority.WARN) { "Could not query page" }
        null
    }

    init {
        viewModelScope.launch {
            queryPage()?.let { page ->
                uiState = PageDetailsViewState(
                    name = page.name,
                    content = parser.parse(page.markdown)
                )
            }
        }
    }
}
