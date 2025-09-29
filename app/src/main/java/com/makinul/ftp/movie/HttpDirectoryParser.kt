package com.makinul.ftp.movie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup

// FileItem and FileType enums remain the same as before

class HttpDirectoryParser {

    private val client = OkHttpClient()

    // This is the core function that does the work
    suspend fun parseUrl(url: String): List<FileItem> {
        // Always run network and parsing operations on a background thread
        return withContext(Dispatchers.IO) {
            try {
                // 1. Fetch the HTML page from the server
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val html = response.body.string()

                // 2. Parse the HTML using Jsoup
                val doc = Jsoup.parse(html)
                val links = doc.select("a[href]") // Find all anchor tags with an "href"

                // 3. Convert links to FileItem objects
                links.mapNotNull { link ->
                    val href = link.attr("href")
                    val name = link.text()

                    // Ignore parent directory links like "?C=N;O=D" or "../"
                    if (name.contains("Parent Directory") || href.startsWith("?")) {
                        return@mapNotNull null
                    }

                    // Construct the full path for the item
                    val fullPath = if (url.endsWith("/")) "$url$href" else "$url/$href"

                    // 4. Determine if it's a video file or a directory
                    when {
                        href.endsWith(".mkv") || href.endsWith(".mp4") ->
                            FileItem(name, FileType.VIDEO, fullPath)

                        href.endsWith("/") ->
                            FileItem(name, FileType.DIRECTORY, fullPath)

                        else -> null // Ignore other file types
                    }
                }
            } catch (e: Exception) {
                // Handle network errors, etc.
                e.printStackTrace()
                emptyList()
            }
        }
    }
}