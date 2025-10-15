package com.hraj9258.fastfood.food.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hraj9258.fastfood.core.presentation.ui.theme.FastFoodTheme
import fastfood.composeapp.generated.resources.Res
import fastfood.composeapp.generated.resources.profile_picture
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileTopBar()
        ProfilePicture()
        Spacer(modifier = Modifier.height(24.dp))
        UserInfoSection()
        Spacer(modifier = Modifier.height(16.dp))
        EditProfileButton()
        Spacer(modifier = Modifier.height(16.dp))
        LogoutButton(
            onLogoutClick = onLogoutClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets(),
        title = {
            Text("Profile", fontWeight = FontWeight.SemiBold)
        },
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
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun ProfilePicture() {
    Box(contentAlignment = Alignment.BottomEnd) {
        Image(
            painter = painterResource(Res.drawable.profile_picture),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(4.dp, Color(0xFFFFF3E0), CircleShape),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFFFE8C00))
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit Profile Picture",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun UserInfoSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                RoundedCornerShape(16.dp),
                {
                    radius = 8F
                    spread = 12F
//                    offset = Offset(0f, 4f)
                    color = Color(0xFFf9f9f9)
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserInfoRow(
                icon = Icons.Default.Person,
                label = "Full Name",
                value = "Adrian Hajdin"
            )
            UserInfoRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = "adrian@jsmastery.com"
            )
            UserInfoRow(
                icon = Icons.Default.Phone,
                label = "Phone number",
                value = "+1 555 123 4567"
            )
            UserInfoRow(
                icon = Icons.Default.LocationOn,
                label = "Address 1 - (Home)",
                value = "123 Main Street, Springfield, IL 62704"
            )
            UserInfoRow(
                icon = Icons.Default.LocationOn,
                label = "Address 2 - (Work)",
                value = "221B Rose Street, Foodville, FL 12345"
            )
        }
    }
}

@Composable
fun UserInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFFE8C00).copy(0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFFFE8C00)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, color = Color.Gray, fontSize = 12.sp)
            Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

@Composable
fun EditProfileButton() {
    OutlinedButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.outlinedButtonColors().copy(
            containerColor = Color(0xFFFE8C00).copy(0.1f),
        ),
        border = BorderStroke(1.dp, Color(0xFFFE8C00))
    ) {
        Text("Edit Profile", color = Color(0xFFFE8C00), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LogoutButton(
    onLogoutClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onLogoutClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.outlinedButtonColors().copy(
            containerColor = Color(0xFFF14141).copy(0.1f),
        ),
        border = BorderStroke(1.dp, Color(0xFFF14141))
    ) {
        Icon(
            Icons.AutoMirrored.Filled.Logout,
            contentDescription = "Logout",
            tint = Color(0xFFF14141)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Logout", color = Color(0xFFF14141), fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    FastFoodTheme {
        ProfileScreen()
    }
}
