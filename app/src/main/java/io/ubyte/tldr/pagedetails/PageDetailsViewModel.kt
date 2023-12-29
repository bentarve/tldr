package io.ubyte.tldr.pagedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.tldr.Pages
import io.ubyte.tldr.pagedetails.PageDetailsViewState.PageDetails
import io.ubyte.tldr.pagedetails.PageDetailsViewState.QueryError
import io.ubyte.tldr.store.PageStore
import io.ubyte.tldr.usecases.MarkdownParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class PageDetailsViewModel @Inject constructor(
    navArgs: SavedStateHandle,
    private val store: PageStore,
    private val parser: MarkdownParser
) : ViewModel() {
    private val _uiState = MutableStateFlow<PageDetailsViewState>(PageDetails())
    val uiState = _uiState.asStateFlow()

    private val pageId: Long? = navArgs[Pages.pageArg]

    private suspend fun queryPage() {
        try {
            requireNotNull(pageId)
            val page = store.queryPage(pageId)
            val pageContent = parser(page.markdown).also { require(it.isNotEmpty()) }
            _uiState.update {
                PageDetails(
                    pageName = page.name,
                    pageContent = pageContent
                )
            }
        } catch (e: Exception) {
            val errorMessage = "Could not query page"
            _uiState.update { QueryError(errorMessage) }
            logcat(LogPriority.WARN) { errorMessage }
        }
    }

    init {
        viewModelScope.launch { queryPage() }
    }
}
