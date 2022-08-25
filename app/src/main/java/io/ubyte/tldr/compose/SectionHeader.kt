package io.ubyte.tldr.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        maxLines = 1,
        style = MaterialTheme.typography.h3
    )
}
