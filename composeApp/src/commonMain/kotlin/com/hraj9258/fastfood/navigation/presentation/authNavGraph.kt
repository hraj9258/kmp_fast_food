package com.hraj9258.fastfood.navigation.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hraj9258.fastfood.auth.presentation.SignInScreenRoot
import com.hraj9258.fastfood.auth.presentation.SignUpScreenRoot
import com.hraj9258.fastfood.navigation.domain.MainNavigationGraph

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){
    navigation<MainNavigationGraph.AuthNavGraph>(
        startDestination = MainNavigationGraph.SignIn,
    ) {
        composable<MainNavigationGraph.SignIn>() {
            SignInScreenRoot(
                onNavigateToSignUp = {
                    navController.navigate(MainNavigationGraph.SignUp) {
                        popUpTo<MainNavigationGraph.SignIn> { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<MainNavigationGraph.SignUp>() {
            SignUpScreenRoot(
                onNavigateToSignIn = {
                    navController.navigate(MainNavigationGraph.SignIn) {
                        popUpTo<MainNavigationGraph.SignUp> { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
