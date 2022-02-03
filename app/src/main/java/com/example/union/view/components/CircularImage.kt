package com.example.union.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CircularImage(image:Any?,border:Int = 0,size:Int = 80){
    Box(
        modifier = Modifier
            .border(
                border.dp,
                MaterialTheme.colors.primary, CircleShape
            )
            .clip(CircleShape)
            .size(size.dp)
    ) {
        GlideImage(
            imageModel = image,
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 250),
        )
    }
}