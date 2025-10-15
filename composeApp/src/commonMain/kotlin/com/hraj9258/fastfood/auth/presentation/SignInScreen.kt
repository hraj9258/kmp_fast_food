package com.hraj9258.fastfood.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hraj9258.fastfood.auth.presentation.components.AuthContainer
import com.hraj9258.fastfood.auth.presentation.components.AuthContainerType
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import com.hraj9258.fastfood.core.presentation.ui.theme.PrimaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel(),
    onNavigateToSignUp: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SignInScreen(
        modifier = modifier,
        state = state,
        onAction = { action -> viewModel.onAction(action) },
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
fun SignInScreen(
    state: AuthUiState,
    modifier: Modifier = Modifier,
    onAction: (AuthAction) -> Unit,
    onNavigateToSignUp: () -> Unit ,
) {
    val primaryTextColor = Color(0xFF212121)
    val secondaryTextColor = Color(0xFF757575)
    AuthContainer(
        modifier = modifier,
        authContainerType = AuthContainerType.SignIn,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(AuthAction.OnEmailChange(it)) },
            label = { Text("Email address", color = secondaryTextColor) },
            modifier = Modifier.fillMaxWidth()
                .semantics { contentType = ContentType.EmailAddress },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = primaryTextColor,
                unfocusedTextColor = primaryTextColor,
                cursorColor = PrimaryColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(AuthAction.OnPasswordChange(it)) },
            label = { Text("Password", color = secondaryTextColor) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentType = ContentType.Password },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = primaryTextColor,
                unfocusedTextColor = primaryTextColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            enabled = !state.loading,
            onClick = {
                onAction(AuthAction.OnSignIn)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            if (state.loading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = secondaryTextColor, fontSize = 14.sp)) {
                    append("Don't have an account? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Sign Up")
                }
            },
            modifier = Modifier.clickable(enabled = !state.loading) { onNavigateToSignUp() }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FastFoodTheme {
        SignInScreen(
            state = AuthUiState(),
            modifier = Modifier.fillMaxSize(),
            onAction = {},
            onNavigateToSignUp = {}
        )
    }
}