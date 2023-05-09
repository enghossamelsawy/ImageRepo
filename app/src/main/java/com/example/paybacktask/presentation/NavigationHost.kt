package com.example.paybacktask.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paybacktask.presentation.ui.composeui.DetailsScreen
import com.example.paybacktask.presentation.ui.composeui.SearchImageScreen
import com.example.paybacktask.presentation.viewmodel.MainViewModel

const val MAIN_SCREEN = "mainScreen"
const val DETAILS_SCREEN = "DetailsScreen"

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MainViewModel>()
    NavHost(navController = navController, startDestination = MAIN_SCREEN) {
        composable(route = MAIN_SCREEN) {
            SearchImageScreen(viewModel) {
                navController.navigate("$DETAILS_SCREEN/${it.id}")

            }
        }
        composable("$DETAILS_SCREEN/{itemId}") {

                backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "0"
            DetailsScreen(viewModel, itemId.toInt())
        }
    }

}