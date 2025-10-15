package com.hraj9258.fastfood.food.search.domain

data class FoodItem(
    val id: String,
    val name: String,
    val price: Float,
    val imageUrl: String,
    val categories: String,
)

data class FoodCategory(
    val id: String,
    val name: String,
)