package com.example.union.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.union.R
import com.example.union.Screen
import com.example.union.model.FriendList
import com.example.union.model.MainModel
import com.example.union.model.PostList
import com.example.union.model.PostModel
import com.example.union.view.components.CircularImage
import com.example.union.view.components.CircularProgressBar
import com.example.union.view.components.SearchBar
import com.example.union.viewModel.MainViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    when(val state = viewModel.postUiState.collectAsState().value){
        is MainViewModel.PostUiState.Empty -> {}
        is MainViewModel.PostUiState.Error -> {}
        is MainViewModel.PostUiState.Loaded -> MainUI(state.data, navController = navController)
        is MainViewModel.PostUiState.Loading -> {
            CircularProgressBar()
        }
    }
}

@Composable
fun MainUI(data: PostModel,navController: NavController) {
    LazyColumn(Modifier.fillMaxSize()){
        item{
            Spacer(modifier = Modifier.height(16.dp))
            ProfileDetails(navController = navController)
            Box(Modifier.padding(16.dp)) {
                SearchBar()
            }
            FriendList()
        }
        items(data.PostList) { item ->
            Box(Modifier.padding(start = 16.dp,end= 16.dp,top = 16.dp)) {
                PostListItem(item)
            }
        }

    }
}

@Composable
fun ProfileDetails(mainViewModel: MainViewModel = hiltViewModel(), navController: NavController) {
    when (val userData = mainViewModel.mainUiState.collectAsState().value) {
        is MainViewModel.MainUiState.Loaded -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Hello,",style = MaterialTheme.typography.h6,color=Color.Gray)
                    Text(text = userData.data.Name, style = MaterialTheme.typography.h5)
                }
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .size(80.dp)
                        .clickable {
                            navController.navigate(route = Screen.Profile.route)
                        }
                )
            }
        }
    }

}


@Composable
fun FriendList(mainViewModel: MainViewModel = hiltViewModel()) {

    when (val items = mainViewModel.mainUiState.collectAsState().value) {
        is MainViewModel.MainUiState.Loaded ->
            GenerateFriendList(items.data.FriendList)
    }


}

@Composable
fun GenerateFriendList(friendList: List<FriendList>) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        item {
            AddFriends()
        }
        items(friendList) { item ->
            FriendListItem(image = item.ProfileImage)


        }
    }
}

@Composable
fun AddFriends() {
    Surface(modifier = Modifier.padding(start = 10.dp)) {
        Card(elevation = 3.dp, shape = CircleShape) {
            Surface() {
                Box(
                    modifier = Modifier
                        .size(79.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Gray
                    )
                }
            }

        }
    }
}

@Composable
fun FriendListItem(image: String) {
    Surface(modifier = Modifier.padding(start = 10.dp)) {
        CircularImage(image = image, border = 2)
    }
}



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
                IconButton(onClick = { /*TODO*/ }) {
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

@Composable
fun ErrorDialog(message: String) {
    Text("message")
}

@Composable
fun LoadScreen(data: MainModel) {
    Text(text = data.toString())
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), viewModel = viewModel())
}