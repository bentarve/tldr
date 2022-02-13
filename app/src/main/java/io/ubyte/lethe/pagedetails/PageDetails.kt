package io.ubyte.lethe.pagedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material.MaterialRichText
import io.ubyte.lethe.core.ui.components.Toolbar

@Composable
fun PageDetails(
    viewModel: PageDetailsViewModel,
    navigateUp: () -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Column {
            Toolbar(navigateUp) {
                Text(
                    text = viewModel.pageName,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
            MaterialRichText(Modifier.padding(16.dp)) {
                Markdown(viewModel.pageContent)
            }
        }
    }
}
