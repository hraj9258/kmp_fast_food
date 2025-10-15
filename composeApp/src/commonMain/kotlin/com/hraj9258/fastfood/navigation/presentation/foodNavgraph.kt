package com.hraj9258.fastfood.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hraj9258.fastfood.food.SharedViewModel
import com.hraj9258.fastfood.food.cart.presentation.CartScreenRoot
import com.hraj9258.fastfood.food.home.presentation.FoodHomeScreenRoot
import com.hraj9258.fastfood.food.profile.presentation.ProfileScreen
import com.hraj9258.fastfood.food.search.presentation.FoodSearchScreenRoot
import com.hraj9258.fastfood.navigation.domain.MainNavigationGraph
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.foodNavGraph(
    navController: NavHostController,
    onLogoutClick: () -> Unit = {},
){
    navigation<MainNavigationGraph.FoodNavGraph>(
        startDestination = MainNavigationGraph.Home,
    ) {
        composable<MainNavigationGraph.Home>() {
            val sharedViewModel = it.sharedKoinViewModel<SharedViewModel>(navController)
            FoodHomeScreenRoot(
                sharedViewModel = sharedViewModel,
                onCartClick = {
                    navController.navigate(MainNavigationGraph.Cart){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<MainNavigationGraph.Search>() {
            val sharedViewModel = it.sharedKoinViewModel<SharedViewModel>(navController)
            FoodSearchScreenRoot(
                sharedViewModel = sharedViewModel,
                onCartClick = {
                    navController.navigate(MainNavigationGraph.Cart){
                        launchSingleTop = true
                    }
                },
                onFoodItemClick = {

                }
            )
        }
        composable<MainNavigationGraph.Cart>() {
            val sharedViewModel = it.sharedKoinViewModel<SharedViewModel>(navController)
            CartScreenRoot(
                sharedViewModel = sharedViewModel
            )
        }
        composable<MainNavigationGraph.Profile>() {
            ProfileScreen(
                onLogoutClick = onLogoutClick
            )
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}