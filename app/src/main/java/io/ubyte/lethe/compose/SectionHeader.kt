package io.ubyte.lethe.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.h3,
        maxLines = 1
    )
}
