package io.ubyte.lethe.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(
    navigateUp: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .height(TopBarHeight)
            .fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackButton(navigateUp)
            content()
        }
    }
}

private val TopBarHeight = 56.dp
