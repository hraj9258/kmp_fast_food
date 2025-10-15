package com.hraj9258.fastfood.food.search.presentation

import com.hraj9258.fastfood.food.search.domain.FoodItem

sealed interface SearchAction {
    /**
     * Triggered when the user types in the search bar.
     * @param query The new text in the search bar.
     */
    data class OnSearchQueryChanged(val query: String) : SearchAction

    /**
     * Triggered when the user taps on a category chip.
     * @param category The name of the category that was selected.
     */
    data class OnCategorySelected(val category: String) : SearchAction

    /**
     * Triggered to explicitly refresh or retry the data fetch.
     */
    object OnSearch : SearchAction

    data class OnAddToCart(val foodItem: FoodItem): SearchAction
}