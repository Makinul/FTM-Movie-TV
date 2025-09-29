package com.makinul.ftp.movie

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.net.ftp.FTPClient

class FtpClient(private val serverIp: String) {

    // You would manage credentials and connection state here
    private val ftp = FTPClient()

    suspend fun listFiles(path: String): List<FileItem> {
        // This should be run in a background thread (e.g., withContext(Dispatchers.IO))
        return withContext(Dispatchers.IO) {
            try {
                // Connect and login logic would go here
                // ftp.connect(serverIp)
                // ftp.login("user", "password")
                // ftp.enterLocalPassiveMode()
                // ftp.setFileType(FTP.BINARY_FILE_TYPE)

                // Placeholder for actual connection
                Log.d("FtpClient", "Fetching files for path: $path")

                // THIS IS THE CORE LOGIC:
                // val files: Array<FTPFile> = ftp.listFiles(path)
                // return files.mapNotNull { ftpFile ->
                //    // Convert FTPFile to your FileItem, filtering for videos and directories
                // }

                // --- FAKE DATA FOR DEMONSTRATION ---
                // Replace this with your actual FTP call
                when (path) {
                    "/" -> {
                        listOf(
                            FileItem("Movies", FileType.DIRECTORY, "/Movies"),
                            FileItem("TV Shows", FileType.DIRECTORY, "/TV Shows"),
                        )
                    }
                    "/Movies" -> {
                        listOf(
                            FileItem("My Awesome Movie.mkv", FileType.VIDEO, "/Movies/My Awesome Movie.mkv")
                        )
                    }
                    else -> {
                        emptyList()
                    }
                }

            } catch (e: Exception) {
                // Handle exceptions (e.g., connection failed)
                emptyList()
            } finally {
                // ftp.disconnect()
            }
        }
    }
}