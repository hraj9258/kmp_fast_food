package com.hraj9258.fastfood.food.search.data.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@JsonIgnoreUnknownKeys
@Serializable
data class FoodItemDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("rating") val rating: Float,
    @SerialName("calories") val calories: Int,
    @SerialName("protein") val protein: Int,
    @SerialName("price") val price: Float,
    @SerialName("categories") val categories: String,
)

@Serializable
data class FoodCategoryDto(
    val name: String,
    val description: String
)