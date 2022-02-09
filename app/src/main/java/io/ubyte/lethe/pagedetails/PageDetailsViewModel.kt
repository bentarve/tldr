package io.ubyte.lethe.pagedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.Destinations.PAGE_ID
import io.ubyte.lethe.FindPage
import io.ubyte.lethe.model.Page
import io.ubyte.lethe.store.PageStore
import javax.inject.Inject

@HiltViewModel
class PageDetailsViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val store: PageStore
) : ViewModel() {
    val page: Page
        get() = store.queryPage(checkNotNull(savedState.get(PAGE_ID)))
}
