package io.ubyte.tldr.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        maxLines = 1
    )
}

// https://stackoverflow.com/questions/71812710/can-no-longer-view-jetpack-compose-previews-failed-to-instantiate-one-or-more-c
@Composable
@Preview
private fun HeadingTextPreview() {
    SectionHeader(text = "Section title")
}
