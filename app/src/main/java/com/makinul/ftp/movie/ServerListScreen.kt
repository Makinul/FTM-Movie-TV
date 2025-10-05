package com.makinul.ftp.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.*


// Data class for a server entry
data class FtpServer(val name: String, val ipAddress: String)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ServerListScreen(
    onServerSelected: (FtpServer) -> Unit
) {

    // In a real app, this would come from a database or ViewModel
    val servers by remember {
        mutableStateOf(
            listOf(
                FtpServer("Local FTp", "http://172.16.50.4/"),
                FtpServer("Live TV", "https://imotv.net/")
            )
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 58.dp, vertical = 38.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "My FTP Servers",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // List of servers
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(servers) { server ->
                    ServerCard(server = server, onClick = { onServerSelected(server) })
                }

                // Button to add a new server
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* TODO: Show 'Add Server' dialog */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add Server"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add New Server")
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ServerCard(server: FtpServer, onClick: () -> Unit) {
    // The Card provides excellent default focus behavior for TV
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.6f) // Make cards wide but centered
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(server.name, style = MaterialTheme.typography.titleMedium)
                Text(server.ipAddress, style = MaterialTheme.typography.bodySmall)
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = null
            )
        }
    }
}