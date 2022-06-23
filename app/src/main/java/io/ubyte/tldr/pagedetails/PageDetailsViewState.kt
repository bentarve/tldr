package io.ubyte.tldr.pagedetails

import androidx.compose.ui.text.AnnotatedString

data class PageDetailsViewState(
    val name: String = "",
    val content: AnnotatedString? = null
)
