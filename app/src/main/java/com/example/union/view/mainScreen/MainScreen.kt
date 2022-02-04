package com.example.union.view.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.union.R
import com.example.union.Screen
import com.example.union.model.FriendList
import com.example.union.model.MainModel
import com.example.union.view.mainScreen.components.PostListItem
import com.example.union.view.globalComponents.CircularProgressBar
import com.example.union.view.globalComponents.SearchBar
import com.example.union.view.mainScreen.components.FriendListItem


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    when (val state = viewModel.mainUiState.collectAsState().value) {

        MainViewModel.MainUiState.Empty -> {}
        is MainViewModel.MainUiState.Error -> {}
        is MainViewModel.MainUiState.Loaded -> MainUI(
            userData = state.userData,
            navController = navController,
            viewModel = viewModel
        )
        MainViewModel.MainUiState.Loading -> CircularProgressBar()
    }
}

@Composable
private fun MainUI(userData: MainModel, navController: NavController, viewModel: MainViewModel) {
    val posts = viewModel.getPostPagination().collectAsLazyPagingItems()

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileDetails(navController = navController, data = userData)
            Box(Modifier.padding(16.dp)) {
                SearchBar()
            }
            GenerateFriendList(userData.FriendList)
        }
        items(posts){ item->
            Box(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                item?.let {
                    PostListItem(item = it)
                }
            }
        }
    }
}

@Composable
fun ProfileDetails(navController: NavController, data: MainModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Hello,", style = MaterialTheme.typography.h6, color = Color.Gray)
            Text(text = data.Name, style = MaterialTheme.typography.h5)
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
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), viewModel = viewModel())
}