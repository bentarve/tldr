package io.ubyte.tldr.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NorthWest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
                Text(
                    text = page.name,
                )
                if (page.platform != "Common") {
                    Text(
                        text = page.platform,
                    )
                }
            }
            if (icon != Icon.FREQUENT_PAGES) {
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    imageVector = Icons.Rounded.NorthWest, // todo extract icon
                    contentDescription = null
                )
            }
        }
    }
}
