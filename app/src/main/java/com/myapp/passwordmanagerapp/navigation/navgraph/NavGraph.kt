package com.myapp.passwordmanagerapp.navigation.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.passwordmanagerapp.navigation.screens.Screen
import com.myapp.passwordmanagerapp.presentation.accounts.AccountScreen
import com.myapp.passwordmanagerapp.presentation.accounts.viewmodels.AccountViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(
    navController: NavHostController,

) {
    val accountViewModel: AccountViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.AccountScreen.route
    ) {
        composable(route = Screen.AccountScreen.route) {
            AccountScreen(
                // Other parameters
                viewModel = accountViewModel,
            )
//
            // Additional composable destinations can be added if needed
        }

    }
}