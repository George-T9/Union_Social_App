package com.example.union

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.union.view.ChatScreen
import com.example.union.view.mainScreen.MainScreen
import com.example.union.view.ProfileScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {

            MainScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.ChatList.route) {
            ChatScreen(navController = navController)
        }
    }

}