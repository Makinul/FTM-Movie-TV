package com.makinul.ftp.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text

// Represents a file or folder on the server
enum class FileType { DIRECTORY, VIDEO, OTHER }
data class FileItem(val name: String, val type: FileType, val path: String)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FileBrowserScreen(
    viewModel: FileBrowserViewModel,
    onFileSelected: (FileItem) -> Unit
) {
    // Collect the state from the ViewModel
    val files by viewModel.fileList.collectAsState()
    val currentPath by viewModel.currentPath.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 58.dp, vertical = 38.dp)
        ) {
            // Header showing the current path
            Text(
                "Browsing: $currentPath",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // "Go Up" button, only shown if not at the root
                if (currentPath != "/") {
                    item {
                        ListItem(
                            selected = false,
                            onClick = { viewModel.navigateUp() },
                            headlineContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_folder_up),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                // List of files and folders
                items(files) { file ->
                    ListItem(
                        selected = true,
                        onClick = {
                            if (file.type == FileType.DIRECTORY) {
                                viewModel.loadDirectory(file.path)
                            } else {
                                onFileSelected(file)
                            }
                        },
                        headlineContent = {
                            val iconRes = if (file.type == FileType.DIRECTORY) {
                                R.drawable.ic_folder
                            } else {
                                R.drawable.ic_movie
                            }
                            Icon(painter = painterResource(id = iconRes), contentDescription = null)
                        }
                    )
                }
            }
        }
    }
}