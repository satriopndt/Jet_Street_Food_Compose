package com.satriopndt.jetstreetfood.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Cart: Screen("cart")
    object Profile: Screen("profile")
    object DetailFoodies: Screen("home/{foodId}"){
        fun createRoute(foodId: Long) = "home/$foodId"
    }
}
