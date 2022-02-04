package com.example.union.view.globalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.union.R
import com.example.union.view.chatScreen.ChatViewModel

@Composable
fun SearchBar(viewModel: ViewModel? = null) {
    var text by remember {
        mutableStateOf("")
    }
    Surface(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)) {

        TextField(
            value = text,
            onValueChange = {
                text = it
                if (viewModel is ChatViewModel) {
                    viewModel.searchFriendChat(it)
                }
            },
            placeholder = { Text(text = "Search friend") },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(25.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            leadingIcon = {
                Row() {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Magenta)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "",
                            modifier = Modifier
                                .background(color = Color.Magenta)
                                .clip(CircleShape),
                            tint = Color.White
                        )
                    }
                }
            }
        )
    }
}