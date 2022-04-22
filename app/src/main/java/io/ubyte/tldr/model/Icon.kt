package io.ubyte.tldr.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.LabelImportant
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import io.ubyte.tldr.R

enum class Icon(
    val imageVector: ImageVector,
    val description: String
) { // todo extract icons
    RECENT_PAGE(Icons.Rounded.History, "Recently visited page"),
    FREQUENT_PAGE(Icons.Rounded.LabelImportant, "Frequently visited page"),
    SEARCH_RESULT(Icons.Rounded.Search, "Search Result")
}
