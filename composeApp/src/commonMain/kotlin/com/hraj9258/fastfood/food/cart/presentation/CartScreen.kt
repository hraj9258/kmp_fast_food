package com.hraj9258.fastfood.food.cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.hraj9258.fastfood.core.presentation.ui.theme.ErrorColor
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import com.hraj9258.fastfood.core.presentation.ui.theme.GreenColor
import com.hraj9258.fastfood.core.presentation.ui.theme.PrimaryColor
import com.hraj9258.fastfood.food.CartState
import com.hraj9258.fastfood.food.SharedViewModel
import com.hraj9258.fastfood.food.cart.domain.CartItem
import com.hraj9258.fastfood.food.search.domain.FoodItem
import fastfood.composeapp.generated.resources.Res
import fastfood.composeapp.generated.resources.empty_state
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun CartScreenRoot(
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
) {
    val cartState by sharedViewModel.state.collectAsStateWithLifecycle()
    CartScreen(
        cartState = cartState,
        modifier = modifier,
        onAction = { action ->
            when (action) {
                is CartScreenActions.OnQuantityChange -> {
                    sharedViewModel.updateQuantity(action.foodItem, action.quantity)
                }

                is CartScreenActions.OnRemoveItem -> {
                    sharedViewModel.removeFromCart(action.foodItem)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartState: CartState,
    modifier: Modifier = Modifier,
    onAction: (CartScreenActions) -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        CartTopBar()
        DeliveryLocation()
        Spacer(modifier = Modifier.height(8.dp))
        if (cartState.cartItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(cartState.cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onQuantityChange = { newQuantity ->
                            onAction(CartScreenActions.OnQuantityChange(item.foodItem, newQuantity))
                        },
                        onDelete = { foodItem ->
                            onAction(CartScreenActions.OnRemoveItem(foodItem))
                        },
                        modifier = Modifier
                            .animateItem()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            PaymentSummary(cartState = cartState)
            Spacer(modifier = Modifier.height(16.dp))
            OrderNowButton()
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.empty_state),
                        contentDescription = "Cart is empty!"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Your cart is empty",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text("Add some items to your cart to get started!")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar() {
    TopAppBar(
        windowInsets = WindowInsets(),
        title = { },
        navigationIcon = {
            IconButton(onClick = { /* Handle back */ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle search */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun DeliveryLocation() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "DELIVERY LOCATION",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )
            Text(
                text = "Home",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        OutlinedButton(
            onClick = { /* Handle change location */ },
            shape = RoundedCornerShape(20.dp),
            border = ButtonDefaults.outlinedButtonBorder().copy(
                width = Dp.Hairline,
                brush = SolidColor(PrimaryColor)
            )
        ) {
            Text("Change Location", color = PrimaryColor)
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    modifier: Modifier = Modifier,
    onQuantityChange: (Int) -> Unit = {},
    onDelete: (FoodItem) -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(true) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                RoundedCornerShape(16.dp)
            ) {
                radius = 8F
//                offset = Offset(0f, 4f)
                color = Color(0xFFf1f1f1)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = item.foodItem.imageUrl,
                contentDescription = item.foodItem.name,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryColor.copy(alpha = 0.1f))
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.foodItem.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Row {
                    Column {
                        Text(
                            text = "$${(item.foodItem.price * 100).roundToInt() / 100.0}",
                            color = PrimaryColor,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        QuantitySelector(
                            quantity = item.quantity,
                            onQuantityChange = onQuantityChange
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            onDelete(item.foodItem)
                        },
                        modifier = Modifier
                            .align(Alignment.Bottom)
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete Item",
                            tint = ErrorColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Default.Remove,
                contentDescription = "Decrease quantity",
                tint = PrimaryColor
            )
        }
        Text(
            text = quantity.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = { onQuantityChange(quantity + 1) },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Increase quantity",
                tint = PrimaryColor
            )
        }
    }
}

@Composable
fun PaymentSummary(
    modifier: Modifier = Modifier,
    cartState: CartState
) {
    val totalItems = cartState.cartItems.size

    val totalValue = remember(cartState.cartItems) {
        cartState.cartItems.sumOf { cartItem ->
            (cartItem.foodItem.price * cartItem.quantity).toDouble()
        }.toFloat()
    }

    val discount = 5f

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder().copy(width = Dp.Hairline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Payment Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            SummaryRow("Total Items (${totalItems})", "$${totalValue}")
            SummaryRow("Delivery Fee", "Free", valueColor = GreenColor)
            SummaryRow("Discount", "-$${discount}", valueColor = GreenColor)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = Dp.Hairline)
            SummaryRow("Total", "$${totalValue - discount}", isTotal = true)
        }
    }
}

@Composable
fun SummaryRow(
    label: String,
    value: String,
    valueColor: Color = Color.Black,
    isTotal: Boolean = false
) {
    val fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = fontWeight, color = if (isTotal) Color.Black else Color.Gray)
        Text(value, fontWeight = FontWeight.Bold, color = valueColor)
    }
}

@Composable
fun OrderNowButton() {
    Button(
        onClick = { /* Handle Order */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
    ) {
        Text("Order Now", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}


@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    FastFoodTheme {
        CartScreen(
            cartState = CartState(
                cartItems = listOf(
                    CartItem(
                        foodItem = FoodItem(
                            id = "123",
                            name = "Burger",
                            price = 5.99f,
                            imageUrl = "https://example.com/burger.jpg",
                            categories = "Fast Food"
                        ),
                        quantity = 1,
                    )
                )
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
}

