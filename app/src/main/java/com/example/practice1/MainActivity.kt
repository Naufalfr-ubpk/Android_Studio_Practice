package com.example.practice1

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.example.practice1.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            Practice1Theme(darkTheme = isDarkMode) {
                var currentScreen by remember { mutableStateOf("onboarding") }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (isDarkMode) Color(0xFF121212) else Color(0xFFF2FCEB)
                ) {
                    when (currentScreen) {
                        "onboarding" -> ModernOnboardingScreen(onNavigate = { currentScreen = "auth" })
                        "auth" -> AuthScreen(
                            onNavigateBack = { currentScreen = "onboarding" },
                            onLoginSuccess = { currentScreen = "main" }
                        )
                        "main" -> MainScreen(
                            onLogout = { currentScreen = "auth" },
                            isDarkMode = isDarkMode,
                            onThemeChange = { isDarkMode = it }
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 1. DATA PRODUK (DENGAN KATEGORI)
// ==========================================
data class Product(val id: Int, val name: String, val price: String, val imageRes: Int, val category: String)

val dummyProducts = listOf(
    Product(1, "Kentang", "15.000", R.drawable.img_item1, "Sayur"),
    Product(2, "Kol", "8.000", R.drawable.img_item2, "Sayur"),
    Product(3, "Sawi Hijau", "10.000", R.drawable.img_item3, "Sayur"),
    Product(4, "Tomat", "15.000", R.drawable.img_item4, "Sayur"),
    Product(5, "Wortel", "12.000", R.drawable.img_item5, "Sayur"),
    Product(6, "Apel", "30.000", R.drawable.img_item6, "Buah"),
    Product(7, "Jeruk", "25.000", R.drawable.img_item7, "Buah"),
    Product(8, "Mangga", "20.000", R.drawable.img_item8, "Buah"),
    Product(9, "Pepaya", "8.000", R.drawable.img_item9, "Buah"),
    Product(10, "Pisang", "15.000", R.drawable.img_item10, "Buah")
)

// ==========================================
// 2. KODE ONBOARDING
// ==========================================
@Composable
fun ModernOnboardingScreen(onNavigate: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.65f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = "Selamat Datang\ndi V-Fresh!",
                fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 26.sp,
                color = VFreshDark, textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 12.dp), lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                painter = painterResource(id = R.drawable.img_onboarding),
                contentDescription = "Ilustrasi Belanja Kesegaran",
                modifier = Modifier.fillMaxWidth().weight(1.5f), contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(60.dp))
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().fillMaxHeight(0.44f),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp), color = Color.White, shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize().navigationBarsPadding()
                    .padding(top = 28.dp, start = 24.dp, end = 24.dp, bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Segar Tiap Hari,\nLangsung ke Depan Pintu",
                    fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 25.sp,
                    color = Color(0xFF1A1A1A), textAlign = TextAlign.Center, lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Lupakan ribetnya ke pasar. Kami pilihkan sayur dan buah terbaik, 100% dari petani lokal untuk keluarga Anda.",
                    fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 20.sp,
                    color = Color(0xFF424242), textAlign = TextAlign.Center, lineHeight = 25.sp, modifier = Modifier.padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onNavigate,
                    modifier = Modifier.fillMaxWidth(0.70f).height(56.dp)
                        .shadow(elevation = 3.dp, shape = CircleShape, ambientColor = Color.Black.copy(alpha = 0.6f), spotColor = Color.Black.copy(alpha = 0.9f)),
                    colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary), shape = CircleShape
                ) {
                    Text(text = "Mulai Belanja", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}

// ==========================================
// 3. KODE AUTH
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(onNavigateBack: () -> Unit, onLoginSuccess: () -> Unit) {
    BackHandler { onNavigateBack() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()

    fun isValidPassword(pass: String): Boolean {
        val hasLowercase = pass.any { it.isLowerCase() }
        val hasNumberOrSymbol = pass.any { !it.isLetter() }
        return pass.length >= 6 && hasLowercase && hasNumberOrSymbol
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .clickable(indication = null, interactionSource = interactionSource) {
                focusManager.clearFocus()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.img_auth),
                contentDescription = "Ilustrasi Login/Register",
                modifier = Modifier.fillMaxWidth().height(180.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (isLogin) "Masuk ke Akun" else "Daftar Akun Baru",
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isLogin) "Selamat datang kembali di V-Fresh!" else "Lengkapi data di bawah untuk bergabung",
                fontFamily = NunitoFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                color = Color(0xFF5D5D5D),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontFamily = NunitoFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 15.dp),
                    textAlign = TextAlign.Center,
                    lineHeight = 25.sp
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                    errorMessage = ""
                },
                isError = emailError,
                label = { Text("Email", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email", tint = VFreshPrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VFreshPrimary,
                    focusedLabelColor = VFreshPrimary,
                    cursorColor = VFreshPrimary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                    errorMessage = ""
                },
                isError = passwordError,
                label = { Text("Password", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock", tint = VFreshPrimary) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VFreshPrimary,
                    focusedLabelColor = VFreshPrimary,
                    cursorColor = VFreshPrimary
                ),
                singleLine = true
            )

            if (!isLogin) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = false
                        errorMessage = ""
                    },
                    isError = confirmPasswordError,
                    label = { Text("Konfirmasi Password", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Lock", tint = VFreshPrimary) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VFreshPrimary,
                        focusedLabelColor = VFreshPrimary,
                        cursorColor = VFreshPrimary
                    ),
                    singleLine = true
                )
            }

            if (isLogin) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "Lupa Password?",
                        fontFamily = RobotoFont,
                        fontWeight = FontWeight.Bold,
                        color = VFreshDark,
                        fontSize = 17.sp,
                        modifier = Modifier.clickable {  }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    emailError = false
                    passwordError = false
                    confirmPasswordError = false
                    errorMessage = ""
                    if (email.isBlank() && password.isBlank()) {
                        emailError = true
                        passwordError = true
                        errorMessage = "Email dan Password tidak\nboleh kosong"
                    } else if (email.isBlank()) {
                        emailError = true
                        errorMessage = "Email tidak boleh kosong"
                    } else if (password.isBlank()) {
                        passwordError = true
                        errorMessage = "Password tidak boleh kosong"
                    } else if (!isValidPassword(password)) {
                        passwordError = true
                        errorMessage = "Password min. 6 karakter, harus\nmenggunakan kecil, angka,\ndan simbol"
                    } else if (!isLogin && password != confirmPassword) {
                        confirmPasswordError = true
                        errorMessage = "Konfirmasi password tidak cocok"
                    } else {
                        onLoginSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
                    .shadow(elevation = 3.dp, shape = CircleShape, ambientColor = Color.Black.copy(alpha = 0.6f), spotColor = Color.Black.copy(alpha = 0.9f)),
                colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                shape = CircleShape
            ) {
                Text(
                    text = if (isLogin) "Masuk" else "Daftar Sekarang",
                    fontFamily = RobotoFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                onClick = { onLoginSuccess() },
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth(0.85f).height(56.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Logo Google", modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (isLogin) "Masuk dengan Google" else "Daftar dengan Google",
                        fontFamily = RobotoFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)) {
                Text(
                    text = if (isLogin) "Belum punya akun? " else "Sudah punya akun? ",
                    fontFamily = NunitoFont,
                    color = Color.Gray,
                    fontSize = 17.sp
                )
                Text(
                    text = if (isLogin) "Daftar" else "Masuk",
                    fontFamily = RobotoFont,
                    fontWeight = FontWeight.Bold,
                    color = VFreshPrimary,
                    fontSize = 17.sp,
                    modifier = Modifier.clickable {
                        isLogin = !isLogin
                        email = ""
                        password = ""
                        confirmPassword = ""
                        emailError = false
                        passwordError = false
                        confirmPasswordError = false
                        errorMessage = ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(350.dp))
        }
    }
}

// ==========================================
// 4. KODE MAIN SCREEN & NAVIGASI
// ==========================================
@Composable
fun MainScreen(onLogout: () -> Unit, isDarkMode: Boolean, onThemeChange: (Boolean) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val cartItems = remember { mutableStateListOf<Pair<Product, String>>() }
    val favoriteItems = remember { mutableStateListOf<Product>() }

    var subScreen by remember { mutableStateOf("none") }

    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val bottomBarColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val indicatorColor = if (isDarkMode) Color(0xFF2C2C2C) else Color(0xFFF2FCEB)

    if (selectedProduct != null) {
        ProductDetailScreen(
            product = selectedProduct!!,
            onBack = { selectedProduct = null },
            isDarkMode = isDarkMode,
            isFavorite = favoriteItems.contains(selectedProduct),
            onFavoriteToggle = {
                if (favoriteItems.contains(selectedProduct)) favoriteItems.remove(selectedProduct)
                else favoriteItems.add(selectedProduct!!)
            },
            onAddToCart = { weight ->
                cartItems.add(selectedProduct!! to weight)
                selectedProduct = null
            }
        )
    } else {
        Scaffold(
            bottomBar = {
                if (subScreen == "none") {
                    NavigationBar(containerColor = bottomBarColor, tonalElevation = 8.dp) {
                        NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0 }, icon = { Icon(Icons.Default.Home, contentDescription = "Beranda", modifier = Modifier.size(30.dp)) }, label = { Text("Beranda", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                        NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifikasi", modifier = Modifier.size(30.dp)) }, label = { Text("Notifikasi", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                        NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2 }, icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Pesanan", modifier = Modifier.size(30.dp)) }, label = { Text("Pesanan", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                        NavigationBarItem(selected = selectedTab == 3, onClick = { selectedTab = 3 }, icon = { Icon(Icons.Default.Person, contentDescription = "Akun", modifier = Modifier.size(30.dp)) }, label = { Text("Akun", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(bgColor)) {
                Crossfade(targetState = subScreen, label = "screen_transition") { currentSubScreen ->
                    when (currentSubScreen) {
                        "none" -> {
                            when (selectedTab) {
                                0 -> HomeScreen(onProductClick = { p -> selectedProduct = p }, isDarkMode = isDarkMode, onThemeChange = onThemeChange, favoriteItems = favoriteItems, onNavigate = { route -> subScreen = route })
                                1 -> NotificationScreen(isDarkMode = isDarkMode)
                                2 -> OrdersScreen(isDarkMode = isDarkMode)
                                3 -> ProfileScreen(isDarkMode = isDarkMode, onLogout = onLogout)
                            }
                        }
                        "cart" -> CartScreen(cartItems = cartItems, isDarkMode = isDarkMode, onBack = { subScreen = "none" }, onNavigateToCheckout = { subScreen = "checkout" })
                        "favorite" -> FavoriteScreen(favoriteItems = favoriteItems, isDarkMode = isDarkMode, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" })
                        "kategori_buah" -> CategoryScreen(categoryName = "Buah", isDarkMode = isDarkMode, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" })
                        "kategori_sayur" -> CategoryScreen(categoryName = "Sayur", isDarkMode = isDarkMode, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" })
                        "checkout" -> CheckoutScreen(onBack = { subScreen = "cart" }, isDarkMode = isDarkMode)
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. KODE HOME SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    favoriteItems: MutableList<Product>,
    onNavigate: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val gridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()

    val searchQuery = remember { mutableStateOf("") }
    val submittedQuery = remember { mutableStateOf("") }

    val displayedProducts = dummyProducts.filter {
        it.name.contains(submittedQuery.value, ignoreCase = true)
    }

    val performSearchAndScroll = {
        submittedQuery.value = searchQuery.value
        focusManager.clearFocus()

        if (searchQuery.value.isNotEmpty()) {
            coroutineScope.launch {
                gridState.animateScrollToItem(index = 3)
            }
        }
    }

    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val searchBarColor = if (isDarkMode) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val textColorDark = if (isDarkMode) Color.White else VFreshDark

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Surface(color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 14.dp)
                    .offset(y = (-4).dp)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { onNavigate("favorite") }, modifier = Modifier.size(40.dp)) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorit",
                        tint = VFreshPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Surface(
                    shape = CircleShape,
                    shadowElevation = 3.dp,
                    color = searchBarColor,
                    modifier = Modifier
                        .weight(0.60f)
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = VFreshPrimary,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .clickable { performSearchAndScroll() }
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                            if (searchQuery.value.isEmpty()) {
                                Text(
                                    text = "Cari yang anda mau ...",
                                    fontFamily = NunitoFont,
                                    color = Color.Gray,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            androidx.compose.foundation.text.BasicTextField(
                                value = searchQuery.value,
                                onValueChange = {
                                    searchQuery.value = it
                                    if (it.isEmpty()) { submittedQuery.value = "" }
                                },
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 15.sp, fontFamily = NunitoFont, color = textColor),
                                singleLine = true,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
                                keyboardActions = androidx.compose.foundation.text.KeyboardActions(onSearch = { performSearchAndScroll() }),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(onClick = { onNavigate("cart") }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Keranjang", tint = VFreshPrimary, modifier = Modifier.size(28.dp))
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    if (!isDarkMode) {
                        Surface(
                            modifier = Modifier
                                .width(54.dp)
                                .height(32.dp),
                            shape = CircleShape,
                            shadowElevation = 4.dp,
                            color = Color.White,
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {}
                    }

                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onThemeChange(it) },
                        modifier = Modifier,
                        thumbContent = {
                            Box(
                                modifier = Modifier
                                    .size(SwitchDefaults.IconSize)
                                    .shadow(elevation = 3.dp, shape = CircleShape)
                                    .background(if (isDarkMode) Color(0xFF3A3A3A) else Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDarkMode) {
                                    Icon(
                                        imageVector = Icons.Default.DarkMode,
                                        contentDescription = "Bulan",
                                        tint = Color(0xFFE8D0FF),
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.LightMode,
                                        contentDescription = "Matahari",
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Transparent,
                            uncheckedThumbColor = Color.Transparent,
                            checkedTrackColor = Color(0xFF6A0DAD).copy(alpha = 0.6f),
                            uncheckedTrackColor = Color.Transparent,
                            uncheckedBorderColor = Color.Transparent,
                            checkedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                val bannerImages = listOf(R.drawable.img_promo, R.drawable.img_promo2)
                val pagerState = rememberPagerState(pageCount = { bannerImages.size })

                LaunchedEffect(Unit) {
                    while (true) {
                        delay(5000)
                        val nextPage = (pagerState.currentPage + 1) % bannerImages.size
                        pagerState.animateScrollToPage(nextPage)
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { page ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = bannerImages[page]),
                                contentDescription = "Promo Banner",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(bannerImages.size) { iteration ->
                            val color = if (pagerState.currentPage == iteration) VFreshPrimary else Color.LightGray
                            Box(modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(8.dp))
                        }
                    }
                }
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Kategori Sayur (Dikasih efek klik biar animasi "terbang" kerasa)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth().clickable { onNavigate("kategori_sayur") }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_cat_sayur),
                                contentDescription = "Sayur",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Sayur", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColorDark)
                    }

                    // Kategori Buah
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth().clickable { onNavigate("kategori_buah") }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_cat_buah),
                                contentDescription = "Buah",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Buah", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColorDark)
                    }
                }
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Rekomendasi",
                        fontFamily = PoppinsFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }

            if (displayedProducts.isEmpty()) {
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 40.dp)
                    ) {
                        Text(
                            text = "Maaf, jenis buah atau sayur\nyang anda cari belum\ntersedia",
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFFE53935),
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )
                    }
                }
            } else {
                items(displayedProducts) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}

// ==========================================
// 6. KODE PRODUCT CARD
// ==========================================
@Composable
fun ProductCard(
    product: Product,
    onProductClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(130.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = product.name, fontFamily = PoppinsFont, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp ${product.price} / 1 kg",
                    color = VFreshPrimary,
                    fontFamily = FiraSansFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { onProductClick() },
                    modifier = Modifier.fillMaxWidth().height(38.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Beli", fontFamily = RobotoFont, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

// ==========================================
// 7. KODE DETAIL SCREEN & BOTTOM SHEET
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit,
    isDarkMode: Boolean,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    onAddToCart: (String) -> Unit
) {
    val context = LocalContext.current
    val itemColors = mapOf(
        "Kentang" to Color(0xFFF4D4A5), "Kol" to Color(0xFFB4E179), "Sawi Hijau" to Color(0xFFACCE43), "Tomat" to Color(0xFFD1806B), "Wortel" to Color(0xFFFF9B72),
        "Apel" to Color(0xFFD96679), "Jeruk" to Color(0xFFFFBA4A), "Mangga" to Color(0xFF97B360), "Pepaya" to Color(0xFFDDB256), "Pisang" to Color(0xFFF5D791)
    )

    val headerBackgroundColor = itemColors[product.name] ?: Color(0xFFE8F5E9)

    BackHandler { onBack() }

    // Pajangan vibrantColor agar palette tetap jalan (menghilangkan warning kuning)
    var vibrantColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(product.imageRes) {
        val bitmap = BitmapFactory.decodeResource(context.resources, product.imageRes)
        if (bitmap != null) {
            Palette.from(bitmap).generate { palette -> palette?.vibrantSwatch?.let { vibrantColor = Color(it.rgb) } }
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    var userRating by remember { mutableIntStateOf(0) }

    var kgInput by remember { mutableStateOf("0") }
    var gramInput by remember { mutableStateOf("0") }
    var errorMessage by remember { mutableStateOf("") }
    val maxStockKg = 7.0

    val bgColor = if (isDarkMode) Color(0xFF121212) else Color.White
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val infoCardColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF9F9F9)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray
    val buttonControlColor = if (isDarkMode) Color(0xFF2C2C2C) else Color(0xFFF0F0F0)

    fun validateInput(newKg: String, newGram: String) {
        kgInput = newKg; gramInput = newGram; errorMessage = ""
        val kgValue = newKg.toIntOrNull()
        val gramValue = newGram.toIntOrNull()

        if (newKg.isNotEmpty() && kgValue == null) { errorMessage = "Maaf, Kg harus berupa angka bulat"; return }
        if (newGram.isNotEmpty() && gramValue == null) { errorMessage = "Maaf, Gram harus berupa angka bulat"; return }

        val safeKg = kgValue ?: 0
        val safeGram = gramValue ?: 0
        val totalWeightInKg = safeKg + (safeGram / 1000.0)

        if (safeGram % 50 != 0) { errorMessage = "Maaf, Gram harus kelipatan 50"; return }
        if (totalWeightInKg > maxStockKg) { errorMessage = "Maaf, stok hanya tersedia $maxStockKg Kg"; return }
    }

    Scaffold(
        containerColor = bgColor,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp, top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        kgInput = "0"
                        gramInput = "0"
                        errorMessage = ""
                        showBottomSheet = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Tambah ke Keranjang & Checkout",
                        fontFamily = RobotoFont,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(bgColor)) {
            Box(modifier = Modifier.fillMaxWidth().height(310.dp).background(brush = androidx.compose.ui.graphics.Brush.verticalGradient(colors = listOf(headerBackgroundColor, headerBackgroundColor.copy(alpha = 0.5f), bgColor))))

            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = CircleShape, color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.size(40.dp).clickable { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.padding(8.dp), tint = textColor)
                    }
                }

                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(24.dp)).shadow(8.dp, RoundedCornerShape(24.dp))
                    )

                    // REVISI ICON LOVE: Proporsional & Optical Alignment
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 16.dp) // Posisi sesuai custom lu
                            .size(44.dp) // Ukuran lingkaran dinaikin jadi 44.dp
                            .background(Color.White.copy(alpha = 0.8f), CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onFavoriteToggle() },
                        contentAlignment = Alignment.Center
                    ) {
                        // Pakai persentase (0.65f = 65%) biar otomatis menyesuaikan ukuran luar
                        // Pakai offset 1.5.dp buat narik icon ke bawah biar mata ngelihatnya seimbang
                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.65f)
                                .offset(y = 1.5.dp)
                        ) {
                            // Lapisan 1: Isian (Fill)
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorit",
                                tint = if (isFavorite) VFreshPrimary else Color.White,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Lapisan 2: Stroke (Garis Tepi)
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = Color(0xFFA0A0A0),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = product.name, fontFamily = PoppinsFont, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = textColor)
                        Text(text = "Rp ${product.price} / 1 kg", color = VFreshPrimary, fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..5) {
                            Icon(imageVector = if (i <= userRating) Icons.Filled.Star else Icons.Outlined.Star, contentDescription = "Star $i", tint = if (i <= userRating) Color(0xFFFFD700) else Color.LightGray, modifier = Modifier.size(28.dp).clickable { userRating = i })
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "4.8 (120 Ulasan)", fontFamily = NunitoFont, color = Color.Gray, fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Deskripsi", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${product.name} segar berkualitas tinggi, langsung dipanen dari petani lokal. Cocok untuk memenuhi kebutuhan nutrisi keluarga Anda sehari-hari dengan kesegaran yang terjamin.", fontFamily = NunitoFont, fontSize = 15.sp, color = subTextColor, lineHeight = 24.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(modifier = Modifier.fillMaxWidth().padding(end = 4.dp), colors = CardDefaults.cardColors(containerColor = infoCardColor), shape = RoundedCornerShape(16.dp)) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Outlined.Info, contentDescription = "Info", tint = VFreshPrimary, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Syarat Checkout", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = textColor)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Pesanan baru dapat diproses (checkout) jika total kuantitas belanja mencapai minimal 5 Kg (dapat dikombinasikan dengan yang lain).", fontFamily = NunitoFont, fontSize = 15.sp, color = subTextColor, lineHeight = 22.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() + 20.dp))
                }
            }
        }
    }

    if (showBottomSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val focusManager = LocalFocusManager.current
        val totalGrams = ((kgInput.toIntOrNull() ?: 0) * 1000) + (gramInput.toIntOrNull() ?: 0)
        val isCheckoutReady = totalGrams >= 5000
        val isCartReady = totalGrams > 0

        ModalBottomSheet(onDismissRequest = { focusManager.clearFocus(); showBottomSheet = false }, sheetState = sheetState, containerColor = surfaceColor) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            ) {
                Text("Pilih Kuantitas", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = textColor)
                Text("Stok tersedia: $maxStockKg Kg", fontFamily = NunitoFont, fontSize = 15.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Kilogram (Kg)", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = subTextColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = buttonControlColor, modifier = Modifier.size(40.dp).clickable { focusManager.clearFocus(); val currentKg = kgInput.toIntOrNull() ?: 0; if (currentKg > 0) validateInput((currentKg - 1).toString(), gramInput) }) {
                            Box(contentAlignment = Alignment.Center) { Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = textColor) }
                        }
                        androidx.compose.foundation.text.BasicTextField(
                            value = kgInput, onValueChange = { validateInput(it, gramInput) },
                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = if (errorMessage.contains("Kg", ignoreCase = true) || errorMessage.contains("stok", ignoreCase = true)) Color.Red else textColor),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number), modifier = Modifier.width(60.dp).padding(horizontal = 8.dp)
                        )
                        Surface(shape = CircleShape, color = VFreshPrimary, modifier = Modifier.size(40.dp).clickable { focusManager.clearFocus(); val currentKg = kgInput.toIntOrNull() ?: 0; validateInput((currentKg + 1).toString(), gramInput) }) {
                            Box(contentAlignment = Alignment.Center) { Text("+", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Gram (g)", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = subTextColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = buttonControlColor, modifier = Modifier.size(40.dp).clickable { focusManager.clearFocus(); val currentGram = gramInput.toIntOrNull() ?: 0; if (currentGram >= 50) validateInput(kgInput, (currentGram - 50).toString()) }) {
                            Box(contentAlignment = Alignment.Center) { Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = textColor) }
                        }
                        androidx.compose.foundation.text.BasicTextField(
                            value = gramInput, onValueChange = { validateInput(kgInput, it) },
                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = if (errorMessage.contains("Gram", ignoreCase = true) || errorMessage.contains("stok", ignoreCase = true)) Color.Red else textColor),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number), modifier = Modifier.width(60.dp).padding(horizontal = 8.dp)
                        )
                        Surface(shape = CircleShape, color = VFreshPrimary, modifier = Modifier.size(40.dp).clickable { focusManager.clearFocus(); val currentGram = gramInput.toIntOrNull() ?: 0; validateInput(kgInput, (currentGram + 50).toString()) }) {
                            Box(contentAlignment = Alignment.Center) { Text("+", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White) }
                        }
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = errorMessage, color = Color.Red, fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        if (totalGrams == 0) { errorMessage = "Maaf, Kuantitas tidak boleh kosong" }
                        else if (errorMessage.isEmpty()) { onAddToCart("${kgInput}kg ${gramInput}g"); showBottomSheet = false }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = if (isCartReady && errorMessage.isEmpty()) VFreshPrimary else (if (isDarkMode) Color(0xFF333333) else Color(0xFFEEEEEE))), shape = RoundedCornerShape(12.dp)
                ) { Text(text = "Simpan ke Keranjang", fontFamily = RobotoFont, color = if (isCartReady && errorMessage.isEmpty()) Color.White else Color.Gray, fontWeight = FontWeight.Bold, fontSize = 16.sp) }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        if (!isCheckoutReady) { errorMessage = "Maaf, minimal checkout 5 Kg \n(Dapat dikombinasikan dengan jenis lainnya)" }
                        else if (errorMessage.isEmpty()) { showBottomSheet = false }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = if (isCheckoutReady && errorMessage.isEmpty()) VFreshPrimary else (if (isDarkMode) Color(0xFF444444) else Color(0xFFE0E0E0))), shape = RoundedCornerShape(12.dp)
                ) { Text(text = "Checkout", fontFamily = RobotoFont, color = if (isCheckoutReady && errorMessage.isEmpty()) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 16.sp) }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

// ==========================================
// 8. KODE HALAMAN KERANJANG & FAVORIT
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartItems: MutableList<Pair<Product, String>>, onBack: () -> Unit, onNavigateToCheckout: () -> Unit, isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }
    val checkedItems = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(cartItems.size) {
        cartItems.forEachIndexed { index, _ -> if (!checkedItems.containsKey(index)) { checkedItems[index] = true } }
    }

    val filteredCart = cartItems.mapIndexed { index, pair -> index to pair }.filter {
        it.second.first.name.contains(searchQuery, ignoreCase = true)
    }

    var totalGrams = 0
    cartItems.forEachIndexed { index, pair ->
        if (checkedItems[index] == true) {
            val weightStr = pair.second
            val kgPart = weightStr.substringBefore("kg").trim().toIntOrNull() ?: 0
            val gramPart = weightStr.substringAfter("kg").substringBefore("g").trim().toIntOrNull() ?: 0
            totalGrams += (kgPart * 1000) + gramPart
        }
    }
    val isCheckoutReady = totalGrams >= 5000

    Column(modifier = Modifier.fillMaxSize().background(bgColor).clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }) {
        Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.size(44.dp).clickable { onBack() }) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) }
            }
            Text("Keranjang", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(44.dp))
        }

        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            OutlinedTextField(
                value = searchQuery, onValueChange = { searchQuery = it },
                placeholder = { Text("Cari produk di keranjang...", fontFamily = NunitoFont, color = Color.Gray, fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = VFreshPrimary) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VFreshPrimary, unfocusedBorderColor = Color.Transparent, focusedContainerColor = surfaceColor, unfocusedContainerColor = surfaceColor),
                singleLine = true
            )
        }

        if (cartItems.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.img_empty_cart), contentDescription = "Keranjang Kosong", modifier = Modifier.size(200.dp), contentScale = ContentScale.Fit)
                Spacer(modifier = Modifier.height(20.dp))
                Text("Halaman keranjang masih kosong nih,\nyuk tambahkan buah dan sayur segar!", fontFamily = NunitoFont, fontSize = 17.sp, color = subTextColor, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp), lineHeight = 24.sp)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                items(filteredCart) { (originalIndex, pair) ->
                    val product = pair.first
                    val weight = pair.second
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), colors = CardDefaults.cardColors(containerColor = surfaceColor), elevation = CardDefaults.cardElevation(2.dp)) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = checkedItems[originalIndex] == true, onCheckedChange = { checkedItems[originalIndex] = it }, colors = CheckboxDefaults.colors(checkedColor = VFreshPrimary))
                            Image(painter = painterResource(id = product.imageRes), contentDescription = product.name, modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColor)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Kuantitas: $weight", fontFamily = FiraSansFont, fontSize = 15.sp, color = subTextColor)
                            }
                            Text("Hapus", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 15.sp, modifier = Modifier.clickable {
                                cartItems.removeAt(originalIndex); checkedItems.remove(originalIndex)
                            })
                        }
                    }
                }
            }

            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 16.dp, color = surfaceColor) {
                Column(modifier = Modifier.padding(16.dp).navigationBarsPadding()) {
                    if (!isCheckoutReady && cartItems.isNotEmpty()) {
                        Text("Maaf, minimal checkout 5 Kg (Pilih item lagi)", color = Color.Red, fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Button(
                        onClick = onNavigateToCheckout, enabled = isCheckoutReady, modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary, disabledContainerColor = Color(0xFFD3D3D3)), shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Checkout", fontFamily = RobotoFont, color = if (isCheckoutReady) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteScreen(favoriteItems: MutableList<Product>, onProductClick: (Product) -> Unit, onBack: () -> Unit, isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }

    val filteredFavs = favoriteItems.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize().background(bgColor).clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }) {
        Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.size(44.dp).clickable { onBack() }) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) }
            }
            Text("Favorit", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(44.dp))
        }

        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            OutlinedTextField(
                value = searchQuery, onValueChange = { searchQuery = it },
                placeholder = { Text("Cari produk favorit...", fontFamily = NunitoFont, color = Color.Gray, fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = VFreshPrimary) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VFreshPrimary, unfocusedBorderColor = Color.Transparent, focusedContainerColor = surfaceColor, unfocusedContainerColor = surfaceColor),
                singleLine = true
            )
        }

        if (favoriteItems.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.img_empty_favorite), contentDescription = "Favorit Kosong", modifier = Modifier.size(200.dp), contentScale = ContentScale.Fit)
                Spacer(modifier = Modifier.height(20.dp))
                Text("Halaman favorit masih kosong,\nyuk cari produk kesukaanmu!", fontFamily = NunitoFont, fontSize = 17.sp, color = subTextColor, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp), lineHeight = 24.sp)
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredFavs) { product ->
                    Box {
                        ProductCard(product = product, onProductClick = { onProductClick(product) })
                        Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.8f), modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(36.dp).clickable { favoriteItems.remove(product) }) {
                            Icon(Icons.Filled.Favorite, contentDescription = "Hapus", tint = Color.Red, modifier = Modifier.padding(6.dp))
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// KODE HALAMAN FILTER (CATEGORY SCREEN)
// ==========================================
@Composable
fun CategoryScreen(categoryName: String, isDarkMode: Boolean, onProductClick: (Product) -> Unit, onBack: () -> Unit) {
    val filteredProducts = dummyProducts.filter { it.category.equals(categoryName, ignoreCase = true) }
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White

    // Animasi transisi membesar
    var isAnimated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isAnimated = true
    }

    Column(modifier = Modifier.fillMaxSize().background(bgColor)) {
        // Tombol Back dipisah di atas biar gambar nggak mepet
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = surfaceColor,
                shadowElevation = 2.dp,
                modifier = Modifier.size(44.dp).clickable { onBack() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor)
                }
            }
        }

        // Jarak lega antara tombol back dan gambar hero
        Spacer(modifier = Modifier.height(1.dp))

        // Gambar Hero: Full Kiri-Kanan
        Image(
            painter = painterResource(id = if (categoryName == "Buah") R.drawable.ic_cat_buah else R.drawable.ic_cat_sayur),
            contentDescription = categoryName,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                // Simulasi efek membesar pas baru masuk halaman
                .padding(if (isAnimated) 0.dp else 24.dp),
            contentScale = ContentScale.FillWidth // Proporsional dan mentok kiri-kanan
        )

        // Teks Judul Kategori
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-15).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = categoryName,
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                color = textColor,
                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Grid Produk
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredProducts) { product ->
                ProductCard(
                    product = product,
                    onProductClick = { onProductClick(product) }
                )
            }
        }
    }
}

// ==========================================
// 9. KODE CHECKOUT SCREEN
// ==========================================
@Composable
fun CheckoutScreen(onBack: () -> Unit, isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    var selectedPayment by remember { mutableStateOf("Transfer Bank (BCA)") }

    Column(modifier = Modifier.fillMaxSize().background(bgColor)) {
        Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.size(44.dp).clickable { onBack() }) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) }
            }
            Text("Checkout", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(44.dp))
        }

        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp).verticalScroll(rememberScrollState())) {
            Text("Alamat Pengiriman", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            Card(modifier = Modifier.fillMaxWidth().clickable { /* TODO: Navigasi ke ubah alamat */ }, colors = CardDefaults.cardColors(containerColor = surfaceColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Rumah - Naufal Fauzi", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColor)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Jl. HS.Ronggo Waluyo, Puseurjaya, Telukjambe Timur, Karawang", fontFamily = NunitoFont, fontSize = 15.sp, color = subTextColor, lineHeight = 22.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Pilih Metode Pembayaran", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))

            val paymentMethods = listOf(
                Pair("Transfer Bank (BCA)", R.drawable.ic_pay_bca),
                Pair("E-Wallet (GoPay)", R.drawable.ic_pay_gopay),
                Pair("Agen (Alfamart)", R.drawable.ic_pay_alfamart),
                Pair("QRIS", R.drawable.ic_cat_buah) // Ganti icon QRIS kalau udah ada
            )

            paymentMethods.forEach { (name, iconRes) ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { selectedPayment = name }, colors = CardDefaults.cardColors(containerColor = surfaceColor)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = iconRes), contentDescription = name, modifier = Modifier.width(50.dp).height(30.dp), contentScale = ContentScale.Fit)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(name, fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = textColor, modifier = Modifier.weight(1f))
                        RadioButton(selected = selectedPayment == name, onClick = { selectedPayment = name }, colors = RadioButtonDefaults.colors(selectedColor = VFreshPrimary))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Rincian Pembayaran", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = surfaceColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Harga (Produk)", fontFamily = NunitoFont, fontSize = 15.sp, color = subTextColor)
                        Text("Rp 85.000", fontFamily = FiraSansFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = textColor)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Biaya Pengiriman", fontFamily = NunitoFont, fontSize = 15.sp, color = subTextColor)
                        Text("Rp 10.000", fontFamily = FiraSansFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = textColor)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.5f))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Belanja", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
                        Text("Rp 95.000", fontFamily = FiraSansFont, fontWeight = FontWeight.ExtraBold, fontSize = 19.sp, color = VFreshPrimary)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 16.dp, color = surfaceColor) {
            Button(
                onClick = { /* TODO: Aksi Bayar beneran */ },
                modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Bayar Sekarang", fontFamily = RobotoFont, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 19.sp)
            }
        }
    }
}

// ==========================================
// 10. KODE PESANAN, NOTIFIKASI, & AKUN
// ==========================================
@Composable
fun OrdersScreen(isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    var selectedOrderTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Dikemas", "Dikirim", "Selesai")

    Column(modifier = Modifier.fillMaxSize().background(bgColor)) {
        Text("Pesanan Saya", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(vertical = 16.dp), textAlign = TextAlign.Center)

        Row(modifier = Modifier.fillMaxWidth().background(surfaceColor).padding(horizontal = 16.dp)) {
            tabs.forEachIndexed { index, title ->
                Column(
                    modifier = Modifier.weight(1f).clickable { selectedOrderTab = index }.padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = title, fontFamily = NunitoFont, fontWeight = if (selectedOrderTab == index) FontWeight.Bold else FontWeight.SemiBold, fontSize = 15.sp, color = if (selectedOrderTab == index) VFreshPrimary else subTextColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (selectedOrderTab == index) {
                        Box(modifier = Modifier.height(3.dp).fillMaxWidth(0.8f).background(VFreshPrimary, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)))
                    } else {
                        Spacer(modifier = Modifier.height(3.dp))
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val emptyImageRes = when (selectedOrderTab) {
                    0 -> R.drawable.img_order_packed
                    1 -> R.drawable.img_order_sent
                    else -> R.drawable.img_order_done
                }
                val emptyText = when (selectedOrderTab) {
                    0 -> "Belum ada pesanan yang sedang dikemas."
                    1 -> "Belum ada pesanan yang sedang dikirim."
                    else -> "Belum ada riwayat pesanan selesai."
                }

                Image(painter = painterResource(id = emptyImageRes), contentDescription = "Pesanan Kosong", modifier = Modifier.size(220.dp), contentScale = ContentScale.Fit)
                Spacer(modifier = Modifier.height(20.dp))
                Text(emptyText, fontFamily = NunitoFont, fontSize = 17.sp, color = subTextColor, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
            }
        }
    }
}

@Composable
fun NotificationScreen(isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    Column(modifier = Modifier.fillMaxSize().background(bgColor)) {
        Text("Notifikasi", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(vertical = 16.dp), textAlign = TextAlign.Center)

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.img_empty_notif), contentDescription = "Notifikasi Kosong", modifier = Modifier.size(200.dp), contentScale = ContentScale.Fit)
                Spacer(modifier = Modifier.height(20.dp))
                Text("Belum ada notifikasi baru untukmu.", fontFamily = NunitoFont, fontSize = 17.sp, color = subTextColor, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ProfileScreen(isDarkMode: Boolean, onLogout: () -> Unit) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    Column(modifier = Modifier.fillMaxSize().background(bgColor).verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth().background(VFreshPrimary).statusBarsPadding().padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.img_onboarding), contentDescription = "Profile", modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.White), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Naufal Fauzi Rahman", fontFamily = PoppinsFont, fontWeight = FontWeight.ExtraBold, fontSize = 21.sp, color = Color.White)
                    Text("naufal.fauzi@mhs.ubpkarawang.ac.id", fontFamily = NunitoFont, fontSize = 15.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Pengaturan Akun", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = surfaceColor), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(12.dp)) {
                Column {
                    ProfileMenuItem(icon = Icons.Outlined.Info, title = "Atur Alamat Pengiriman", textColor = textColor)
                    HorizontalDivider(color = subTextColor.copy(alpha = 0.2f))
                    ProfileMenuItem(icon = Icons.Default.ShoppingCart, title = "Metode Pembayaran", textColor = textColor)
                    HorizontalDivider(color = subTextColor.copy(alpha = 0.2f))
                    ProfileMenuItem(icon = Icons.Default.Lock, title = "Keamanan Akun", textColor = textColor)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Pusat Bantuan", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            Spacer(modifier = Modifier.height(12.dp))
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = surfaceColor), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(12.dp)) {
                Column {
                    ProfileMenuItem(icon = Icons.Default.Email, title = "Hubungi V-Fresh", textColor = textColor)
                    HorizontalDivider(color = subTextColor.copy(alpha = 0.2f))
                    ProfileMenuItem(icon = Icons.Outlined.Star, title = "Beri Nilai Aplikasi", textColor = textColor)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4C4C)), modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Keluar", fontFamily = RobotoFont, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.height(32.dp).navigationBarsPadding())
        }
    }
}

@Composable
fun ProfileMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, textColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = title, tint = VFreshPrimary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = textColor, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Lihat", tint = Color.Gray, modifier = Modifier.size(18.dp)) // Idealnya ArrowForward, tapi pakai ini dulu sbg placeholder
    }
}