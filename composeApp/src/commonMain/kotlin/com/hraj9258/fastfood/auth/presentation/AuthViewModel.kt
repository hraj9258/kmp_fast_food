package com.hraj9258.fastfood.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hraj9258.fastfood.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState  = _uiState.asStateFlow()

    val authState = repository.isSignedIn
        .map { isSignedIn ->
            AuthState(
                isSignedIn = isSignedIn,
                initialized = true
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AuthState(initialized = false)
        )

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.OnSignIn -> signIn()
            is AuthAction.OnSignUp -> signUp()
            is AuthAction.OnEmailChange -> { onEmailChange(action.email) }
            is AuthAction.OnNameChange -> { onNameChange(action.name) }
            is AuthAction.OnPasswordChange -> { onPasswordChange(action.password) }
        }
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value, error = null) }
    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value.trim(), error = null) }
    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value, error = null) }

    fun signIn() {
            val current = _uiState.value
            if (!isValidEmail(current.email)) {
                _uiState.update { it.copy(error = "Please enter a valid email address") }
                return
            }
            if (current.password.length < 6) {
                _uiState.update { it.copy(error = "Password must be at least 6 characters") }
                return
            }

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            withContext(Dispatchers.IO) {
                val result =repository
                    .signIn(current.email, current.password)

                if (result.isFailure){
                    _uiState.update { s ->
                        s.copy(loading = false, error = result.exceptionOrNull()?.toUserMessage())
                    }
                } else { _uiState.update { it.copy(loading = false) } }
            }
        }
    }

    fun signUp() {
        val current = _uiState.value
        if (current.name.isBlank()) {
            _uiState.update { it.copy(error = "Please enter your name") }
            return
        }
        if (!isValidEmail(current.email)) {
            _uiState.update { it.copy(error = "Please enter a valid email address") }
            return
        }
        if (current.password.length < 6) {
            _uiState.update { it.copy(error = "Password must be at least 6 characters") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            val result =repository
                .signUp(current.name, current.email, current.password)

            if (result.isFailure) {
                _uiState.update { s ->
                    s.copy(loading = false, error = result.exceptionOrNull()?.toUserMessage())
                }
            } else {
                _uiState.update { it.copy(loading = false) }
            }

        }
    }

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

    private fun isValidEmail(email: String): Boolean =
        email.contains('@') && email.substringAfter('@').contains('.')
}

private fun Throwable.toUserMessage(): String = when (message) {
    null -> "Something went wrong"
    else -> this.message ?: "Something went wrong"
}