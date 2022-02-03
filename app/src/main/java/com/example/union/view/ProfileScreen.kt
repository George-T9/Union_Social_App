package com.example.union.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.union.R
import com.example.union.Screen
import com.example.union.model.PhotoList
import com.example.union.model.ProfileModel
import com.example.union.view.components.CircularProgressBar
import com.example.union.viewModel.ProfileViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    when (val state = viewModel.uiState.collectAsState().value) {
        ProfileViewModel.ProfileUiState.Empty -> {}
        is ProfileViewModel.ProfileUiState.Error -> {}
        is ProfileViewModel.ProfileUiState.Loaded -> MainUi(
            state.data,
            navController = navController
        )
        ProfileViewModel.ProfileUiState.Loading -> {
            CircularProgressBar()
        }
    }

}

@Composable
private fun MainUi(data: ProfileModel, navController: NavController) {
    LazyColumn() {
        item {
            ProfileToolBar(data, navController = navController)
            Spacer(modifier = Modifier.height(14.dp))
        }
        items(data.PhotoList) { item ->
            PhotoListItem(item)
        }
    }
}

@Composable
fun ProfileToolBar(data: ProfileModel, navController: NavController) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
                IconButton(onClick = { navController.navigate(route = Screen.ChatList.route) }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                }
            }
            Text("Profile", Modifier.fillMaxWidth(), style = MaterialTheme.typography.h4)
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .size(80.dp)

                )
                Text(data.Name, style = MaterialTheme.typography.h5,)
                Text(data.Description, style = MaterialTheme.typography.h6, color = Color.Gray)
                Text(data.Location, style = MaterialTheme.typography.h6, color = Color.Gray)
            }


            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                Column {
                    Text("Photos")
                    Text(data.PhotosCount.toString(), style = MaterialTheme.typography.h6)
                }
                Column {
                    Text("Followers")
                    Text(data.FollowersCount.toString(), style = MaterialTheme.typography.h6)
                }
                Column {
                    Text("Follows")
                    Text(data.FollowsCount.toString(), style = MaterialTheme.typography.h6)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }

}


@Composable
fun GeneratePhotosList(profileViewModel: ProfileViewModel = hiltViewModel()) {
    when (val state = profileViewModel.uiState.collectAsState().value) {
        is ProfileViewModel.ProfileUiState.Loaded -> {
            LazyColumn {

            }
        }
    }
}

@Composable
fun PhotoListItem(item: PhotoList) {
    Surface(modifier = Modifier.padding(top = 12.dp)) {
        Card(elevation = 4.dp) {
            GlideImage(imageModel = item.Image)
        }
    }


}
