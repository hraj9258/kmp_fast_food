package com.hraj9258.fastfood.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hraj9258.fastfood.food.cart.domain.CartItem
import com.hraj9258.fastfood.food.search.domain.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnAddToCart -> addToCart(action.foodItem)
        }
    }
    fun addToCart(item: FoodItem) = viewModelScope.launch {
        _state.update { currentState ->
            val currentItems = currentState.cartItems

            val existingCartItem = currentItems.find { it.foodItem.id == item.id }

            val updatedItems = if (existingCartItem != null) {
                currentItems.map { cartItem ->
                    if (cartItem.foodItem.id == item.id) {
                        cartItem.copy(quantity = cartItem.quantity + 1)
                    } else {
                        cartItem
                    }
                }
            } else {
                // If it doesn't exist, add a new CartItem to the list
                currentItems + CartItem(foodItem = item, quantity = 1)
            }
            currentState.copy(cartItems = updatedItems)
        }
    }

    fun removeFromCart(foodItem: FoodItem) = viewModelScope.launch {
        _state.update { currentState ->
            val updatedItems = currentState.cartItems.filterNot { cartItem ->
                cartItem.foodItem.id == foodItem.id
            }
            currentState.copy(cartItems = updatedItems)
        }
    }

    fun updateQuantity(foodItem: FoodItem, quantity: Int) = viewModelScope.launch {
        _state.update { currentState ->
            val updatedItems = if (quantity > 0) {
                currentState.cartItems.map { cartItem ->
                    if (cartItem.foodItem.id == foodItem.id) {
                        cartItem.copy(quantity = quantity)
                    } else {
                        cartItem
                    }
                }
            } else {
                // If quantity is 0 or less, remove the item
                currentState.cartItems.filterNot { cartItem ->
                    cartItem.foodItem.id == foodItem.id
                }
            }

            currentState.copy(cartItems = updatedItems)
        }
    }

}