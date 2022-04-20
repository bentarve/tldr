package io.ubyte.tldr.pagedetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = viewModel.pageName) },
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // todo extract
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        MaterialRichText(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Markdown(viewModel.pageContent)
        }
    }
}
