package com.hraj9258.fastfood.navigation.domain

import kotlinx.serialization.Serializable

sealed interface MainNavigationGraph {

    @Serializable
    data object Loading : MainNavigationGraph

    // Nested Nav Graph Auth
    @Serializable
    data object AuthNavGraph : MainNavigationGraph

    @Serializable
    data object SignIn : MainNavigationGraph

    @Serializable
    data object SignUp : MainNavigationGraph


    // Nested Nav Graph Food
    @Serializable
    data object FoodNavGraph : MainNavigationGraph
    @Serializable
    data object Home : MainNavigationGraph

    @Serializable
    data object Search : MainNavigationGraph

    @Serializable
    data object Cart : MainNavigationGraph

    @Serializable
    data object Profile : MainNavigationGraph

}