package com.androidproject.voicenotemanager

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.androidproject.voicenotemanager.DestinationsArgs.CATEGORY_ID_ARG
import com.androidproject.voicenotemanager.ui.categorylist.CategoryList
import com.androidproject.voicenotemanager.ui.categorylist.CategoryListScreen
import com.androidproject.voicenotemanager.ui.notelist.NoteListScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = Destinations.CATEGORY_LIST_ROUTE,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            Destinations.CATEGORY_LIST_ROUTE
        ) {
            CategoryListScreen()
        }
        composable(
            Destinations.NOTE_LIST_ROUTE,
            arguments = listOf(
                navArgument(CATEGORY_ID_ARG) { type = NavType.StringType; nullable = true }
            )
        )
        {entry ->

        }


    }

}