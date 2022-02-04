package com.example.union.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.union.model.ChatModel
import com.example.union.model.FriendsList
import com.example.union.view.components.CircularImage
import com.example.union.view.components.CircularProgressBar
import com.example.union.view.components.SearchBar
import com.example.union.viewModel.ChatViewModel

@Composable
fun ChatScreen(navController: NavController, chatViewModel: ChatViewModel = hiltViewModel()) {
    when (val state = chatViewModel.uiState.collectAsState().value) {
        is ChatViewModel.ChatUiState.Loading -> CircularProgressBar()
        is ChatViewModel.ChatUiState.Loaded -> MainUI(
            navController = navController,
            state.data,
            chatViewModel = chatViewModel
        )
        ChatViewModel.ChatUiState.Empty -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Nothing to show")
            }
        }
        is ChatViewModel.ChatUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(state.message)
            }
        }
    }

}

@Composable
fun MainUI(navController: NavController, data: ChatModel, chatViewModel: ChatViewModel) {
    val chatlist = chatViewModel.showingChatList.observeAsState()
    LazyColumn(Modifier.padding(16.dp)) {
        item {
            ChatToolbar(navController = navController, viewModel = chatViewModel)
            Spacer(modifier = Modifier.height(2.dp))
            SearchBar(chatViewModel)
        }
        chatlist.value?.let {
            items(it) { item ->
                ChatListItem(item = item)
            }
        }

    }
}

@Composable
fun ChatToolbar(navController: NavController, viewModel: ChatViewModel) {

    Column {
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
        }
        Text("Conversation", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(2.dp))

    }
}

@Composable
fun ChatListItem(item: FriendsList) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(image = item.ProfileImage, size = 60)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(item.Name, style = MaterialTheme.typography.h6)
            Text(item.LastMessage, style = MaterialTheme.typography.subtitle1, color = Color.Gray)

        }

    }
}
