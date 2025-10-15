package com.hraj9258.fastfood

import androidx.compose.runtime.*
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import com.hraj9258.fastfood.navigation.presentation.NavContainer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    FastFoodTheme {
        NavContainer()
    }
}