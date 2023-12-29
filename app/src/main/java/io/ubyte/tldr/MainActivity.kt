package io.ubyte.tldr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.ubyte.tldr.theme.TldrTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TldrApp()
        }
    }
}

@Composable
private fun TldrApp() {
    TldrTheme {
        val navController = rememberNavController()
        Surface(Modifier.fillMaxSize()) {
            NavigationHost(navController)
        }
    }
}
