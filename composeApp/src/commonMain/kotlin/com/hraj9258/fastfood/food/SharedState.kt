package com.hraj9258.fastfood.food

import com.hraj9258.fastfood.food.cart.domain.CartItem

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
)