package io.ubyte.lethe.pagedetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material.MaterialRichText

@Composable
fun PageDetails(
    viewModel: PageDetailsViewModel,
    navigateUp: () -> Unit
) {
    Surface(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        MaterialRichText {
            Markdown(content = viewModel.page.markdown)
        }
    }
}
