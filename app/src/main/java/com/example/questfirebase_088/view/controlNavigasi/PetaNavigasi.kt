package com.example.questfirebase_088.view.controlNavigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questfirebase_088.view.DetailSiswaScreen
import com.example.questfirebase_088.view.EditSiswaScreen
import com.example.questfirebase_088.view.EntrySiswaScreen
import com.example.questfirebase_088.view.HalamanEdit
import com.example.questfirebase_088.view.HomeScreen
import com.example.questfirebase_088.view.route.DestinasiDetail
import com.example.questfirebase_088.view.route.DestinasiEntry
import com.example.questfirebase_088.view.route.DestinasiHome

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = {
                    // Mengirim ID sebagai bagian dari Route
                    navController.navigate("${DestinasiDetail.route}/$it")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.itemIdArg) {
                type = NavType.StringType // ID diterima sebagai String
            })
        ) {
            DetailSiswaScreen(
                // Mengirim ID ke layar Edit
                navigateToEditItem = { navController.navigate("${HalamanEdit.route}/$it") },
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }

    }
}