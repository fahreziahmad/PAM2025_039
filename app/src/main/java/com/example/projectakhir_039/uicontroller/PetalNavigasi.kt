package com.example.projectakhir_039.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class PetalNavigasi(val navController: NavHostController) {
    fun navigateTo(path: String) {
        navController.navigate(path) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberPetalNavigasi(
    navController: NavHostController = rememberNavController()
): PetalNavigasi = remember(navController) {
    PetalNavigasi(navController)
}