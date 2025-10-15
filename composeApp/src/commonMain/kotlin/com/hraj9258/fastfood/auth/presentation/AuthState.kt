package com.hraj9258.fastfood.auth.presentation

data class AuthState(
    val isSignedIn: Boolean = false,
    val initialized: Boolean = false,
)

data class AuthUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val loading: Boolean = false
)