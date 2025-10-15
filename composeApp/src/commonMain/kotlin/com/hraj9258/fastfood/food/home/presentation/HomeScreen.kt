package com.hraj9258.fastfood.food.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import com.hraj9258.fastfood.food.CartState
import com.hraj9258.fastfood.food.SharedViewModel
import fastfood.composeapp.generated.resources.Res
import fastfood.composeapp.generated.resources.burger_one
import fastfood.composeapp.generated.resources.burger_two
import fastfood.composeapp.generated.resources.buritto
import fastfood.composeapp.generated.resources.pizza_one
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun FoodHomeScreenRoot(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel = koinViewModel(),
    onCartClick: () -> Unit
) {
    val state by sharedViewModel.state.collectAsStateWithLifecycle()

    FoodHomeScreen(
        cartState = state,
        onCartClick = onCartClick,
        modifier = modifier
    )

}
@Composable
fun FoodHomeScreen(
    cartState: CartState,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HomeTopBar(
            cartState = cartState,
            onCartClick = onCartClick,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SummerComboCard()
        Spacer(modifier = Modifier.height(16.dp))
        CategoryCard(
            title = "BURGERS",
            imageRes = Res.drawable.burger_two,
            backgroundColor = Color(0xFFF5A623),
            imageOnLeft = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryCard(
            title = "PIZZA",
            imageRes = Res.drawable.pizza_one,
            backgroundColor = Color(0xFF004D40),
            imageOnLeft = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryCard(
            title = "BURRITO",
            imageRes = Res.drawable.buritto,
            backgroundColor = Color(0xFFEF6C00),
            imageOnLeft = true
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    cartState: CartState,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit = {}
) {
    TopAppBar(
        windowInsets = WindowInsets(),
        title = {
            Column {
                Text(
                    text = "DELIVER TO",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4A24F)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Rijeka, Croatia",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Change location"
                    )
                }
            }
        },
        actions = {
            BadgedBox(
                badge = {
                    if (cartState.cartItems.isNotEmpty()) {
                        Badge(
                            containerColor = Color(0xFFFFA000),
                            contentColor = Color.White
                        ) { Text("${cartState.cartItems.size}") }
                    }
                },
                modifier = Modifier
                    .clickable(onClick ={onCartClick()})
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2D3748)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = modifier
    )
}

@Composable
fun SummerComboCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE64A19))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SUMMER \nCOMBO",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$10.88",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Image(
                painter = painterResource(Res.drawable.burger_one),
                contentDescription = "Summer Combo",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxHeight()
                    .width(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    imageRes: DrawableResource,
    backgroundColor: Color,
    imageOnLeft: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageOnLeft) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = title,
                    modifier = Modifier.weight(0.5f).fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                CategoryInfo(title = title, modifier = Modifier.weight(0.5f))
            } else {
                CategoryInfo(title = title, modifier = Modifier.weight(0.5f).padding(start = 24.dp))
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = title,
                    modifier = Modifier.weight(0.5f).fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun CategoryInfo(title: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Go to $title",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodHomeScreenPreview() {
    FastFoodTheme {
        FoodHomeScreen(
            cartState = CartState(),
            modifier = Modifier.fillMaxSize(),
            onCartClick = {}
        )
    }
}