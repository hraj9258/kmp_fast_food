package com.hraj9258.fastfood.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun SignUpScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel(),
    onNavigateToSignIn: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        state = state,
        modifier = modifier,
        onAction = { action -> viewModel.onAction(action) },
        onNavigateToSignIn = onNavigateToSignIn
    )
}

@Composable
fun SignUpScreen(
    state: AuthUiState,
    modifier: Modifier = Modifier,
    onAction: (AuthAction) -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    val primaryTextColor = Color(0xFF212121)
    val secondaryTextColor = Color(0xFF757575)
    AuthContainer(
        modifier = modifier,
        authContainerType = AuthContainerType.SignUp,
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = {onAction(AuthAction.OnNameChange(it))},
            label = { Text("Full Name", color = secondaryTextColor) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentType = ContentType.PersonFullName },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = primaryTextColor,
                unfocusedTextColor = primaryTextColor,
                cursorColor = PrimaryColor
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(AuthAction.OnEmailChange(it)) },
            label = { Text("Email address", color = secondaryTextColor) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentType = ContentType.EmailAddress }
            ,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = primaryTextColor,
                unfocusedTextColor = primaryTextColor,
                cursorColor = PrimaryColor
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(AuthAction.OnPasswordChange(it))},
            label = { Text("Password", color = secondaryTextColor) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics{ contentType = ContentType.Password },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = primaryTextColor,
                unfocusedTextColor = primaryTextColor,
                cursorColor = PrimaryColor
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            enabled = !state.loading,
            onClick = { onAction(AuthAction.OnSignUp) },
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
                    text = "Sign Up",
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
                    append("Already have an account? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Sign in")
                }
            },
            modifier = Modifier.clickable(enabled = !state.loading) { onNavigateToSignIn() }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    FastFoodTheme {
        SignUpScreen(
            state = AuthUiState(),
            modifier = Modifier.fillMaxSize(),
            onAction = {},
            onNavigateToSignIn = {}
        )
    }
}