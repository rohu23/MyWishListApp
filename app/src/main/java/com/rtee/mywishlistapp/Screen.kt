package com.rtee.mywishlistapp

sealed class Screen (val route:String){
    object HomeScreen: Screen ("home_screen")
    object AddScreen: Screen ("add_screen")
    object DetailScreen: Screen ("detail_screen")
}