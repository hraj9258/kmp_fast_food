package com.hraj9258.fastfood.auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import fastfood.composeapp.generated.resources.Res
import fastfood.composeapp.generated.resources.login_graphic
import fastfood.composeapp.generated.resources.login_graphic_2
import fastfood.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class AuthContainerType{
    SignIn,
    SignUp
}

@Composable
fun AuthContainer(
    modifier: Modifier,
    authContainerType: AuthContainerType,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val backgroundColor = Color(0xFFF96E5B)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .align(Alignment.TopCenter)
        ) {
            val imageResource = when (authContainerType){
                AuthContainerType.SignIn -> Res.drawable.login_graphic
                AuthContainerType.SignUp -> Res.drawable.login_graphic_2
            }
            Image(
                painter = painterResource(imageResource),
                contentDescription = "Burger background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 55.dp)
                    .height(110.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AuthContainerSignInPreview(){
    FastFoodTheme {
        AuthContainer(
            modifier = Modifier.fillMaxSize(),
            authContainerType = AuthContainerType.SignIn
        )
    }
}

@Preview
@Composable
private fun AuthContainerSignUpPreview(){
    FastFoodTheme {
        AuthContainer(
            modifier = Modifier.fillMaxSize(),
            authContainerType = AuthContainerType.SignUp
        )
    }
}