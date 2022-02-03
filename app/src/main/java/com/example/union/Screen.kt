package com.example.union

sealed class Screen(val route:String){
    object Main:Screen("main_screen")
    object Profile:Screen("profile_screen")
    object ChatList:Screen("chat_screen")
}