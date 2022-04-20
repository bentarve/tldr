package io.ubyte.tldr.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.LabelImportant
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Icon(
    val imageVector: ImageVector,
    val description: String
) {
    RECENT_PAGES(Icons.Rounded.History, "Recent pages"), // todo extract icons
    FREQUENT_PAGES(Icons.Rounded.LabelImportant, "Frequent pages"),
    SEARCH_RESULT(Icons.Rounded.Search, "Search result")
}
