package com.makinul.ftp.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FileBrowserViewModel(serverIp: String) : ViewModel() {

    private val ftpClient = FtpClient(serverIp)

    // Holds the list of files for the UI to observe
    private val _fileList = MutableStateFlow<List<FileItem>>(emptyList())
    val fileList: StateFlow<List<FileItem>> = _fileList

    // Holds the current directory path
    private val _currentPath = MutableStateFlow("/")
    val currentPath: StateFlow<String> = _currentPath

    init {
        // Load the root directory when the ViewModel is created
        loadDirectory("/")
    }

    fun loadDirectory(path: String) {
        viewModelScope.launch {
            _currentPath.value = path
            // THE CALL TO YOUR FTP CLIENT HAPPENS HERE:
            _fileList.value = ftpClient.listFiles(path)
        }
    }

    fun navigateUp() {
        val parentPath = _currentPath.value.substringBeforeLast('/', "/")
        loadDirectory(parentPath.ifEmpty { "/" })
    }
}