package io.ubyte.lethe.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigateUp: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .height(TopBarHeight)
            .fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            navigateUp?.let { BackButton(navigateUp) }
            content()
        }
    }
}

@Composable
private fun BackButton(navigateUp: () -> Unit) {
    IconButton(
        onClick = { navigateUp() },
        Modifier.requiredWidth(IntrinsicSize.Max)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
        )
    }
}

val TopBarHeight = 56.dp
