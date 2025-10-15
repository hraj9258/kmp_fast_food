package com.hraj9258.fastfood.food.search.presentation

import com.hraj9258.fastfood.food.search.domain.FoodCategory
import com.hraj9258.fastfood.food.search.domain.FoodItem

data class SearchState(
    val isLoading: Boolean = false,
    val foodItems: List<FoodItem> = emptyList(),
    val categories: List<FoodCategory> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val error: String? = null
)