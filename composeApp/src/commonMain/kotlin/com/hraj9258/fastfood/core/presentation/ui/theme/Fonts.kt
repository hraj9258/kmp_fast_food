package com.hraj9258.fastfood.core.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import fastfood.composeapp.generated.resources.Quicksand_Bold
import fastfood.composeapp.generated.resources.Quicksand_Light
import fastfood.composeapp.generated.resources.Quicksand_Medium
import fastfood.composeapp.generated.resources.Quicksand_Regular
import fastfood.composeapp.generated.resources.Quicksand_SemiBold
import fastfood.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun quickSandFontFamily() = FontFamily(
    Font(Res.font.Quicksand_Light, weight = FontWeight.Light,),
    Font(Res.font.Quicksand_Regular, weight = FontWeight.Normal),
    Font(Res.font.Quicksand_Medium, weight = FontWeight.Medium),
    Font(Res.font.Quicksand_SemiBold, weight = FontWeight.SemiBold),
    Font(Res.font.Quicksand_Bold, weight = FontWeight.Bold)
)

@Composable
fun quickSandsTypography(modifier: Modifier = Modifier) = Typography.run{
    val fontFamily = quickSandFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}