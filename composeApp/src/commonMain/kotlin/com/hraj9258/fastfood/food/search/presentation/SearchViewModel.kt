package com.hraj9258.fastfood.food.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hraj9258.fastfood.food.search.domain.FoodRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
            fetchCategories()
            fetchMenu()

            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchQueryChanged -> onSearchQueryChanged(action.query)
            is SearchAction.OnCategorySelected -> onCategorySelected(action.category)
            is SearchAction.OnSearch -> fetchMenu()
            else -> Unit
        }
    }


    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = onSearchQueryChanged(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onSearchQueryChanged(query: String) = viewModelScope.launch {
        _state.update { it.copy(searchQuery = query) }
        fetchMenu()
    }


    private fun onCategorySelected(category: String) = viewModelScope.launch {
        _state.update { it.copy(selectedCategory = category) }
        fetchMenu()
    }


    private fun fetchMenu() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        val currentState = _state.value

//        println("getMenu called with query: '${currentState.searchQuery}', category: '${currentState.selectedCategory}'")

        foodRepository.getMenu(
            query = currentState.searchQuery,
            category = if (currentState.selectedCategory == "All") "" else currentState.selectedCategory
        ).onSuccess { items ->
            _state.update {
                it.copy(
                    isLoading = false,
                    foodItems = items,
                )
            }
        }.onFailure { error ->
            _state.update {
                it.copy(
                    isLoading = false,
                    error = error.message ?: "An unexpected error occurred"
                )
            }
        }
    }


    private fun fetchCategories() = viewModelScope.launch {
        val result = foodRepository.getCategories()

        result.onSuccess { categories ->
            _state.update {
                it.copy(categories = categories)
            }
        }.onFailure { error ->
            _state.update {
                it.copy(error = error.message ?: "Failed to load categories")
            }
        }
    }
}
