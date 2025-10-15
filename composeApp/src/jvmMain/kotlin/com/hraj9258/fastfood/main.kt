package com.hraj9258.fastfood

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hraj9258.fastfood.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Fast Food",
        ) {
            App()
        }
    }
}