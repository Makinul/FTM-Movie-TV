package com.makinul.ftp.movie

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebBrowseScreen(url: String) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            // Create a new WebView instance
            WebView(context).apply {
                // Apply settings
                settings.javaScriptEnabled = true // CRUCIAL for modern sites
                webViewClient = WebViewClient() // Helps handle navigation within the WebView

                // Load the initial URL
                loadUrl(url)
            }
        },
        update = { webView ->
            // This block is called when the URL state changes
            webView.loadUrl(url)
        }
    )
}