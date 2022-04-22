package io.ubyte.tldr.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NorthWest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ubyte.tldr.model.Icon
import io.ubyte.tldr.model.PageItem

@Composable
fun ListItem(
    pageItem: PageItem,
    openPageDetails: (pageId: Long) -> Unit
) {
    with(pageItem) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openPageDetails(page.id) }
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = icon.description
            )
            Spacer(Modifier.width(18.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = page.name,
                        style = MaterialTheme.typography.h3
                    )
                    if (page.platform != "Common") {
                        Spacer(Modifier.width(20.dp))
                        PlatformBadge(page.platform)
                    }
                }
            }
            if (icon != Icon.FREQUENT_PAGE) {
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    imageVector = Icons.Rounded.NorthWest, // todo extract icon
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun PlatformBadge(platform: String) {
    Box(
        Modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color.DarkGray)
            .padding(horizontal = 5.dp)
    ) {
        Text(
            text = platform,
            style = MaterialTheme.typography.h4,
        )
    }
}
