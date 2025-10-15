package com.hraj9258.fastfood.food.search.data.repository

import com.hraj9258.fastfood.core.data.AppwriteConfig
import com.hraj9258.fastfood.food.search.data.dto.FoodCategoryDto
import com.hraj9258.fastfood.food.search.data.dto.FoodItemDto
import com.hraj9258.fastfood.food.search.data.mappers.toFoodCategory
import com.hraj9258.fastfood.food.search.data.mappers.toFoodItem
import com.hraj9258.fastfood.food.search.domain.FoodCategory
import com.hraj9258.fastfood.food.search.domain.FoodItem
import com.hraj9258.fastfood.food.search.domain.FoodRepository
import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Query
import com.jamshedalamqaderi.kmp.appwrite.services.TablesDB

class AppwriteFoodRepository(
    private val client: Client
) : FoodRepository {
    override suspend fun getMenu(query: String, category: String): Result<List<FoodItem>> = runCatching {
        val queries = mutableListOf<String>()

        if (query.isNotBlank()) {
            queries.add(Query.search("name", query))
        }

        if (category.isNotBlank()) {
            queries.add(Query.equal("categories", category))
        }

        val tdb = TablesDB(client).listRows(
            databaseId = AppwriteConfig.DATABASE_ID,
            tableId = AppwriteConfig.MENU_COLLECTION_ID,
            queries = queries,
            nestedType = FoodItemDto.serializer()
        )
        val menuItems = tdb.rows.map {
            val foodItemDto = it.data
            val foodItemId = it.id
            foodItemDto.toFoodItem(foodItemId)
        }
        menuItems
    }

    override suspend fun getCategories(): Result<List<FoodCategory>> = runCatching {

        val tdb = TablesDB(client).listRows(
            databaseId = AppwriteConfig.DATABASE_ID,
            tableId = AppwriteConfig.CATEGORIES_COLLECTION_ID,
            nestedType = FoodCategoryDto.serializer()
        )

        val categories = tdb.rows.map {
            val foodCategoryDto = it.data
            val categoryId = it.id
            foodCategoryDto.toFoodCategory(categoryId)
        }
        categories
    }
}