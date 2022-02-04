package com.example.union.view.mainScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.union.view.globalComponents.CircularImage

@Composable
fun FriendListItem(image: String?) {
    Surface(modifier = Modifier.padding(start = 10.dp)) {
        CircularImage(image = image, border = 2,)
    }
}