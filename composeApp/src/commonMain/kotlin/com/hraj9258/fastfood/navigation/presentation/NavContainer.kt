package com.hraj9258.fastfood.navigation.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hraj9258.fastfood.auth.presentation.AuthViewModel
import com.hraj9258.fastfood.navigation.domain.MainNavigationGraph
import com.hraj9258.fastfood.navigation.presentation.components.BottomNavigationBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NavContainer(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBarState = when (currentRoute) {
        MainNavigationGraph.Loading::class.qualifiedName,
        MainNavigationGraph.SignIn::class.qualifiedName,
        MainNavigationGraph.SignUp::class.qualifiedName -> { false }
        else -> { true }
    }

    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState.isSignedIn, authState.initialized) {
        if (authState.initialized) {
            val destination =
                if (authState.isSignedIn) MainNavigationGraph.Home else MainNavigationGraph.SignIn

            navController.navigate(destination) {
                popUpTo(MainNavigationGraph.Loading) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    SharedTransitionLayout {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBarState
                ) {
                    BottomNavigationBar(navController = navController)
                }
            },
            containerColor = Color(0xFFF5F5F5),
            modifier = modifier
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = MainNavigationGraph.Loading,
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                // Loading Screen
                composable<MainNavigationGraph.Loading> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                authNavGraph(navController)

                foodNavGraph(navController, onLogoutClick = {
                    authViewModel.signOut()
                })
            }
        }
    }
}
