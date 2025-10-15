package com.hraj9258.fastfood

import androidx.compose.ui.window.ComposeUIViewController
import com.hraj9258.fastfood.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}