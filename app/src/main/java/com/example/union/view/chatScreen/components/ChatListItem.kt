package com.example.union.view.chatScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.union.model.FriendsList
import com.example.union.view.globalComponents.CircularImage

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