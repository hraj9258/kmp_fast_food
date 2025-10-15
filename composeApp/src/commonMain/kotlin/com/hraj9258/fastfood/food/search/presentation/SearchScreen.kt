package com.hraj9258.fastfood.food.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import com.hraj9258.fastfood.core.presentation.ui.theme.PrimaryColor
import com.hraj9258.fastfood.food.CartAction
import com.hraj9258.fastfood.food.CartState
import com.hraj9258.fastfood.food.SharedViewModel
import com.hraj9258.fastfood.food.search.domain.FoodCategory
import com.hraj9258.fastfood.food.search.domain.FoodItem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FoodSearchScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel = koinViewModel(),
    onFoodItemClick: (FoodItem) -> Unit,
    onCartClick: () -> Unit
) {
    val searchState by viewModel.state.collectAsStateWithLifecycle()
    val cartState by sharedViewModel.state.collectAsStateWithLifecycle()
    FoodSearchScreen(
        searchState = searchState,
        cartState = cartState,
        modifier = modifier,
        onAction = { action ->
            when (action) {
                is SearchAction.OnAddToCart -> sharedViewModel.onAction(
                    CartAction.OnAddToCart(
                        action.foodItem
                    )
                )

                else -> Unit
            }
            viewModel.onAction(action)
        },
        onCartClick = onCartClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSearchScreen(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    cartState: CartState,
    onAction: (SearchAction) -> Unit,
    onCartClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        TopBar(
            cartState = cartState,
            onCartClick = onCartClick,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            searchQuery = searchState.searchQuery,
            onSearchQueryChange = {
                onAction(SearchAction.OnSearchQueryChanged(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryChips(
            categories = searchState.categories,
            selectedCategory = searchState.selectedCategory,
            onCategorySelected = {
                onAction(SearchAction.OnCategorySelected(it))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier,
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(searchState.foodItems, key = { index, item -> item.id }) { index, item ->
                FoodItemCard(
                    item,
                    onAddToCart = {
                        onAction(SearchAction.OnAddToCart(item))
                    },
                    modifier = Modifier
                        .animateItem()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    cartState: CartState,
    onCartClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(),
        title = {
            Column {
                Text(
                    text = "SEARCH",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Find your Favorite Food",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select category"
                    )
                }
            }
        },
        actions = {
            BadgedBox(
                badge = {
                    if (cartState.cartItems.isNotEmpty()) {
                        Badge(
                            containerColor = PrimaryColor,
                            contentColor = Color.White
                        ) { Text("${cartState.cartItems.size}") }
                    }
                },
                modifier = Modifier
                    .clickable(onClick = { onCartClick() })
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
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
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Search for any food", color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, CircleShape),
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = PrimaryColor
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearch()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
    )
}

@Composable
fun CategoryChips(
    modifier: Modifier = Modifier,
    categories: List<FoodCategory>,
    selectedCategory: String = "All",
    onCategorySelected: (String) -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            val isSelected = selectedCategory == "All"
            Button(
                onClick = {
                    onCategorySelected("All")
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) PrimaryColor else Color.White,
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                modifier = Modifier
                    .dropShadow(
                        RoundedCornerShape(20.dp),
                        {
                            radius = 8F
                            offset = Offset(0f, 4f)
                            color = Color(0xFFf1f1f1)
                        }
                    )
            ) {
                Text(text = "All")
            }
        }
        items(
            items = categories,
            key = { category -> category.id }
        ) { category ->
            val isSelected = category.id == selectedCategory
            Button(
                onClick = {
                    onCategorySelected(category.id)
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) PrimaryColor else Color.White,
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                modifier = Modifier
                    .dropShadow(
                        RoundedCornerShape(20.dp),
                        {
                            radius = 8F
                            offset = Offset(0f, 4f)
                            color = Color(0xFFf1f1f1)
                        }
                    )
            ) {
                Text(text = category.name)
            }
        }
    }
}

@Composable
fun FoodItemCard(
    item: FoodItem,
    modifier: Modifier = Modifier,
    onAddToCart: (FoodItem) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.55f)
            .height(240.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .dropShadow(
                    RoundedCornerShape(16.dp)
                ) {
                    radius = 8F
                    offset = Offset(0f, 4f)
                    color = Color(0xFFf1f1f1)
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "From $${item.price}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onAddToCart(item) })
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to cart",
                        tint = PrimaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Add to cart",
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(item.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = item.name,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(120.dp),
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun FoodSearchScreenPreview() {
    FastFoodTheme {
        FoodSearchScreen(
            searchState = SearchState(
                categories = listOf(
                    FoodCategory(
                        id = "1",
                        name = "Fast Food",
                    ),
                    FoodCategory(
                        id = "2",
                        name = "Fast Food",
                    ),
                    FoodCategory(
                        id = "3",
                        name = "Fast Food",
                    ),
                    FoodCategory(
                        id = "4",
                        name = "Fast Food",
                    ),
                ),
                foodItems = listOf(
                    FoodItem(
                        id = "123",
                        name = "Burger",
                        price = 5.99f,
                        imageUrl = "https://example.com/burger.jpg",
                        categories = "Fast Food"
                    ),
                    FoodItem(
                        id = "1234",
                        name = "Burger",
                        price = 5.99f,
                        imageUrl = "https://example.com/burger.jpg",
                        categories = "Fast Food"
                    ),
                    FoodItem(
                        id = "123456",
                        name = "Burger",
                        price = 5.99f,
                        imageUrl = "https://example.com/burger.jpg",
                        categories = "Fast Food"
                    ),
                    FoodItem(
                        id = "1234567",
                        name = "Burger",
                        price = 5.99f,
                        imageUrl = "https://example.com/burger.jpg",
                        categories = "Fast Food"
                    ),

                    )
            ),
            cartState = CartState(),
            modifier = Modifier
                .fillMaxSize(),
            onAction = {},
            onCartClick = {}
        )
    }
}

