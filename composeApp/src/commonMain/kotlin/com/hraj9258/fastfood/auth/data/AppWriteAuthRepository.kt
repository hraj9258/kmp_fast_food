package com.hraj9258.fastfood.auth.data

import com.hraj9258.fastfood.auth.domain.AuthRepository
import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.ID
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.services.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppWriteAuthRepository(
    private val client: Client
) : AuthRepository {
    private val account = Account(client)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _isSignedIn = MutableStateFlow(false)
    override val isSignedIn = _isSignedIn.asStateFlow()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        coroutineScope.launch {
            try {
                account.get()
                _isSignedIn.value = true
            } catch (e: AppwriteException) {
                _isSignedIn.value = false
            }
        }
    }

    override suspend fun signUp(name: String, email: String, password: String): Result<Unit> = runCatching{
        account.create(
            userId = ID.unique(),
            email = email,
            password = password,
            name = name
        )
        signIn(email, password).getOrThrow()
    }

    override suspend fun signIn(email: String, password: String): Result<Unit> = runCatching{
         account.createEmailPasswordSession(
            email = email,
            password = password
        )
        _isSignedIn.value = true
    }

    override suspend fun signOut() {
        try {
            account.deleteSessions()
        } finally {
            _isSignedIn.value = false
        }
    }
}