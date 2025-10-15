package com.hraj9258.fastfood.food.cart.presentation

import com.hraj9258.fastfood.food.search.domain.FoodItem

sealed interface CartScreenActions{
    data class OnQuantityChange(val foodItem: FoodItem, val quantity: Int): CartScreenActions

    data class OnRemoveItem(val foodItem: FoodItem): CartScreenActions
}