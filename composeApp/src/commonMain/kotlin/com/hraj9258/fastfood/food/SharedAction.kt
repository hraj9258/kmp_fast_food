package com.hraj9258.fastfood.food

import com.hraj9258.fastfood.food.search.domain.FoodItem

sealed interface CartAction{
    data class OnAddToCart(val foodItem: FoodItem): CartAction
}