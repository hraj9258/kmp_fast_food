package com.hraj9258.fastfood.food.cart.domain

import com.hraj9258.fastfood.food.search.domain.FoodItem

data class CartItem(
    val foodItem: FoodItem,
    val quantity: Int
)