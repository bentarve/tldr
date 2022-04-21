package io.ubyte.tldr.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        maxLines = 1,
        style = MaterialTheme.typography.h3
    )
}

// https://stackoverflow.com/questions/71812710/can-no-longer-view-jetpack-compose-previews-failed-to-instantiate-one-or-more-c
@Composable
@Preview
private fun HeadingTextPreview() {
    SectionHeader(text = "Section title")
}
