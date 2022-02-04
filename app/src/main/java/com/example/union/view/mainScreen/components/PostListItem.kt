package com.example.union.view.mainScreen.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.union.model.PostList
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun PostListItem(item: PostList) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.padding(top = 16.dp),
        elevation = 4.dp
    ) {
        Column {
            GlideImage(imageModel = item.PostImage, contentScale = ContentScale.Crop)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                GlideImage(
                    imageModel = item.ProfileImage,
                    contentScale = ContentScale.Crop,
                    circularReveal = CircularReveal(duration = 250),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(text = item.Name, style = MaterialTheme.typography.h6)
                    Text(text = item.Time)
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {  }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                }
            }
        }
    }
}
