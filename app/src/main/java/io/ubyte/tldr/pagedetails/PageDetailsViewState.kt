package io.ubyte.tldr.pagedetails

import androidx.compose.ui.text.AnnotatedString

sealed interface PageDetailsViewState{
    data class PageDetails(
        val pageName: String = "",
        val pageContent: AnnotatedString = AnnotatedString("")
    ) : PageDetailsViewState

    data class QueryError(val message: String) : PageDetailsViewState
}
