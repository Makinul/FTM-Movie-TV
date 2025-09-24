package com.makinul.ftp.movie

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.core.net.toUri

@Composable
fun VideoPlayerScreen(videoUrl: String, subtitleUrl: String?) {
    val context = LocalContext.current

    // 1. Create and remember the ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // 2. Create the MediaItem for the video
            val videoMediaItemBuilder = MediaItem.Builder().setUri(videoUrl)

            // 3. If a subtitle URL is provided, configure and add it
            subtitleUrl?.let {
                val subtitle = MediaItem.SubtitleConfiguration.Builder(it.toUri())
                    .setMimeType(MimeTypes.APPLICATION_SUBRIP) // MimeType for .srt files
                    .setLanguage("en")
                    .setSelectionFlags(C.SELECTION_FLAG_DEFAULT) // Automatically select this subtitle
                    .build()

                videoMediaItemBuilder.setSubtitleConfigurations(listOf(subtitle))
            }

            // 4. Set the final MediaItem and prepare the player
            setMediaItem(videoMediaItemBuilder.build())
            prepare()
            playWhenReady = true // Start playback automatically
        }
    }

    // 5. Manage the player's lifecycle
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // 6. Display the player in the UI
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    // This enables the default controls which are TV remote friendly
                    useController = true
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}