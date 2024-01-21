package io.ubyte.tldr.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import io.ubyte.tldr.theme.History
import io.ubyte.tldr.theme.LabelImportant

enum class Icon(
    val imageVector: ImageVector,
    val description: String
) {
    RecentPage(Icons.Rounded.History, "Recently visited page"),
    FrequentPage(Icons.Rounded.LabelImportant, "Frequently visited page"),
    SearchResult(Icons.Rounded.Search, "Search Result")
}
