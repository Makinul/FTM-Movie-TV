package com.makinul.ftp.movie

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text

// A data class to hold speed dial item information
data class SpeedDialItem(val name: String, val iconResId: Int)

@Preview
@Composable
fun BrowserHomeScreenPreview() {
    BrowserHomeScreen()
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun BrowserHomeScreen() {
    // Sample data for our speed dial
    val speedDialItems = listOf(
        SpeedDialItem("YouTube", R.drawable.ic_youtube),
        SpeedDialItem("Google News", R.drawable.ic_google_news),
        SpeedDialItem("GitHub", R.drawable.ic_youtube),
        SpeedDialItem("Stack Overflow", R.drawable.ic_google_news),
        SpeedDialItem("Wikipedia", R.drawable.ic_youtube),
        SpeedDialItem("Reddit", R.drawable.ic_google_news),
        SpeedDialItem("FTP", R.drawable.ic_add),
    )

    // Use a dark Surface as the base for the TV UI
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 32.dp)
        ) {
            // 1. Top Bar for Search and Actions
            BrowserTopBar(
                onSearchClick = { Log.v(TAG, "onSearchClick") },
                onBookmarksClick = { Log.v(TAG, "onBookmarksClick") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Speed Dial Grid
            SpeedDialGrid(items = speedDialItems)
        }
    }
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun BrowserTopBar(
    onSearchClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo or App Title
        Text(
            text = "Brave Browser",
            style = MaterialTheme.typography.headlineLarge
        )

        // Action Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search or enter address")
            }
            Button(onClick = onBookmarksClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmarks),
                    contentDescription = "Bookmarks"
                )
            }
        }
    }
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SpeedDialGrid(items: List<SpeedDialItem>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5), // Adjust column count as needed
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items) { item ->
            SpeedDialCard(item = item, onClick = {
                Log.v(TAG, "Clicked on ${item.name}")
            })
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SpeedDialCard(
    item: SpeedDialItem,
    onClick: () -> Unit
) {
    // Card is a great focusable container for TV.
    // It provides visual feedback (scaling) on focus by default.
    Card(
        onClick = onClick,
        modifier = Modifier.aspectRatio(1.5f) // Maintain a consistent aspect ratio
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = item.iconResId),
                contentDescription = null, // Decorative
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

private const val TAG = "BrowserHomeScreen"