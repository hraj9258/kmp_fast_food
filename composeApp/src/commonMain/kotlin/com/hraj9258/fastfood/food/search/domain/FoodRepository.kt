package com.hraj9258.fastfood.food.search.domain

interface FoodRepository {
    suspend fun getMenu(query: String, category: String): Result<List<FoodItem>>
    suspend fun getCategories(): Result<List<FoodCategory>>
}