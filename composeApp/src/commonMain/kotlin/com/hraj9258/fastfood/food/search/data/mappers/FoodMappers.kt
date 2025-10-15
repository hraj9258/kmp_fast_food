package com.hraj9258.fastfood.food.search.data.mappers

import com.hraj9258.fastfood.food.search.data.dto.FoodCategoryDto
import com.hraj9258.fastfood.food.search.data.dto.FoodItemDto
import com.hraj9258.fastfood.food.search.domain.FoodCategory
import com.hraj9258.fastfood.food.search.domain.FoodItem

fun FoodItemDto.toFoodItem(id: String): FoodItem {
    return FoodItem(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        categories = categories
    )
}

fun FoodCategoryDto.toFoodCategory(categoryId: String): FoodCategory {
    return FoodCategory(
        id = categoryId,
        name = name
    )
}