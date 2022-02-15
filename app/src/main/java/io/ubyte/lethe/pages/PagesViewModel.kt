package io.ubyte.lethe.pages

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ubyte.lethe.store.PageStore
import javax.inject.Inject

@HiltViewModel
class PagesViewModel @Inject constructor(
    private val store: PageStore
) : ViewModel() {
    val pager = Pager(PagingConfig(pageSize = 100)) {
        store.queryPagingSource()
    }.flow
}
