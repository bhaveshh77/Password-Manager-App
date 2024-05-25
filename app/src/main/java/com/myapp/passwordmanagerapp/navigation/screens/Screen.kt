package com.myapp.passwordmanagerapp.navigation.screens

sealed class Screen(val route: String) {
    object AccountScreen: Screen("accounts_screen")
}