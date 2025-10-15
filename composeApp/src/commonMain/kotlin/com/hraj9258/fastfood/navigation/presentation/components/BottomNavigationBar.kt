package com.hraj9258.fastfood.navigation.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hraj9258.fastfood.core.presentation.ui.theme.PrimaryColor
import com.hraj9258.fastfood.navigation.domain.MainNavigationGraph

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

val topLevelRoutes = listOf(
    TopLevelRoute(
        name = "Home",
        route = MainNavigationGraph.Home,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
    ),
    TopLevelRoute(
        name = "Search",
        route = MainNavigationGraph.Search,
        selectedIcon = Icons.Filled.Search,
        unSelectedIcon = Icons.Outlined.Search,
    ),
    TopLevelRoute(
        name = "Cart",
        route = MainNavigationGraph.Cart,
        selectedIcon = Icons.Filled.ShoppingCart,
        unSelectedIcon = Icons.Outlined.ShoppingCart,
    ),
    TopLevelRoute(
        name = "Profile",
        route = MainNavigationGraph.Profile,
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navBarDestinations = remember { topLevelRoutes }

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 8.dp,
    ) {
        val selectedItemColor = PrimaryColor
        val unselectedItemColor = Color.Gray

        navBarDestinations.forEachIndexed { index, navBarDestination ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { navDestination ->
                    navDestination.hasRoute(navBarDestination.route::class)
                } == true,
                onClick = {
                    navController.navigate(
                        navBarDestination.route
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(navBarDestination.name, fontWeight = FontWeight.SemiBold) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any { navDestination ->
                                navDestination.hasRoute(navBarDestination.route::class)
                            } == true) {
                            navBarDestination.selectedIcon
                        } else navBarDestination.unSelectedIcon,
                        contentDescription = navBarDestination.name)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedItemColor,
                    selectedTextColor = selectedItemColor,
                    unselectedIconColor = unselectedItemColor,
                    unselectedTextColor = unselectedItemColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}