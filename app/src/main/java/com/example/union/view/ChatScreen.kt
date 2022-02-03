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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.union.Screen
import com.example.union.model.FriendsList
import com.example.union.view.components.CircularImage
import com.example.union.view.components.CircularProgressBar
import com.example.union.view.components.SearchBar
import com.example.union.viewModel.ChatViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ChatScreen(navController: NavController){
    Column(Modifier.padding(16.dp)) {
        ChatCollapsingToolbar(navController = navController)
        Spacer(modifier = Modifier.height(2.dp))
        GenerateChatsList()
    }
}

@Composable
fun ChatCollapsingToolbar(chatViewModel: ChatViewModel= hiltViewModel(),navController: NavController) {
    when(val state = chatViewModel.uiState.collectAsState().value){
        is ChatViewModel.ChatUiState.Loading -> CircularProgressBar()
        is ChatViewModel.ChatUiState.Loaded -> {
            Column {
                Row (
                    Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ){
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(Icons.Filled.ArrowBack,contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.MoreVert,contentDescription = null)
                    }
                }
                Text("Conversation", style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(2.dp))
                SearchBar()
            }
        }
    }
}

@Composable
fun GenerateChatsList(chatViewModel: ChatViewModel = hiltViewModel()) {
    when(val state = chatViewModel.uiState.collectAsState().value){
        is ChatViewModel.ChatUiState.Loaded ->{
            LazyColumn(){
                items(state.data.FriendList){item->
                    ChatListItem(item = item)
                }
            }
        }
    }
}

@Composable
fun ChatListItem(viewModel: ChatViewModel = hiltViewModel(),item:FriendsList) {
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically){
        CircularImage(image = item.ProfileImage, size = 60)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(item.Name, style = MaterialTheme.typography.h6)
            Text(item.LastMessage, style = MaterialTheme.typography.subtitle1)

        }

    }
}
