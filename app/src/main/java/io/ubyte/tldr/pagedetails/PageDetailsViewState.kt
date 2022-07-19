package io.ubyte.tldr.pagedetails

import androidx.compose.ui.text.AnnotatedString

data class PageDetailsViewState(
    val pageName: String = "",
    val pageContent: AnnotatedString? = null
)
