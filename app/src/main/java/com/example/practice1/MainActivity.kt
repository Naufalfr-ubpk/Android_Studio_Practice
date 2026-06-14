package com.example.practice1

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.window.Dialog
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
import androidx.compose.ui.focus.focusProperties
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
            },
            onCheckoutDirect = { weight ->
                cartItems.add(selectedProduct!! to weight)
                selectedProduct = null
                subScreen = "checkout"
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
                                0 -> HomeScreen(onProductClick = { p -> selectedProduct = p }, isDarkMode = isDarkMode, onThemeChange = onThemeChange, onNavigate = { route -> subScreen = route })
                                1 -> NotificationScreen(isDarkMode = isDarkMode)
                                2 -> OrdersScreen(isDarkMode = isDarkMode)
                                3 -> ProfileScreen(isDarkMode = isDarkMode, onLogout = onLogout)
                            }
                        }
                        "cart" -> CartScreen(cartItems = cartItems, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" }, onNavigateToCheckout = { subScreen = "checkout" }, isDarkMode = isDarkMode)
                        "favorite" -> FavoriteScreen(favoriteItems = favoriteItems, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" }, isDarkMode = isDarkMode)
                        "kategori_buah" -> CategoryScreen(categoryName = "Buah", isDarkMode = isDarkMode, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" })
                        "kategori_sayur" -> CategoryScreen(categoryName = "Sayur", isDarkMode = isDarkMode, onProductClick = { p -> selectedProduct = p }, onBack = { subScreen = "none" })
                        "checkout" -> CheckoutScreen(onBack = { subScreen = "cart" }, onNavigateToAddress = { subScreen = "address_list" }, isDarkMode = isDarkMode)
                        "address_list" -> AddressListScreen(onBack = { subScreen = "checkout" }, onAddAddress = { subScreen = "add_address" }, isDarkMode = isDarkMode)
                        "add_address" -> AddAddressScreen(onBack = { subScreen = "address_list" }, isDarkMode = isDarkMode)
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
    onAddToCart: (String) -> Unit,
    onCheckoutDirect: (String) -> Unit // Parameter wajib tambahan
) {
    val context = LocalContext.current
    val itemColors = mapOf(
        "Kentang" to Color(0xFFF4D4A5), "Kol" to Color(0xFFB4E179), "Sawi Hijau" to Color(0xFFACCE43), "Tomat" to Color(0xFFD1806B), "Wortel" to Color(0xFFFF9B72),
        "Apel" to Color(0xFFD96679), "Jeruk" to Color(0xFFFFBA4A), "Mangga" to Color(0xFF97B360), "Pepaya" to Color(0xFFDDB256), "Pisang" to Color(0xFFF5D791)
    )

    val headerBackgroundColor = itemColors[product.name] ?: Color(0xFFE8F5E9)

    BackHandler { onBack() }

    var vibrantColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(product.imageRes) {
        val bitmap = BitmapFactory.decodeResource(context.resources, product.imageRes)
        if (bitmap != null) {
            Palette.from(bitmap).generate { palette -> palette?.vibrantSwatch?.let { vibrantColor = Color(it.rgb) } }
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    var showSuccessOverlay by remember { mutableStateOf(false) } // State modal centang hijau
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

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 16.dp)
                            .size(44.dp)
                            .background(Color.White.copy(alpha = 0.8f), CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onFavoriteToggle() },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.65f)
                                .offset(y = 1.5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorit",
                                tint = if (isFavorite) VFreshPrimary else Color.White,
                                modifier = Modifier.fillMaxSize()
                            )

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

        ModalBottomSheet(onDismissRequest = { focusManager.clearFocus(); showBottomSheet = false }, sheetState = sheetState, containerColor = surfaceColor) {
            val totalGrams = ((kgInput.toIntOrNull() ?: 0) * 1000) + (gramInput.toIntOrNull() ?: 0)
            val isCheckoutReady = totalGrams >= 5000
            val isCartReady = totalGrams > 0

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus()
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .navigationBarsPadding()
                        .imePadding()
                ) {
                    Text("Pilih Kuantitas", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = textColor)
                    Text("Stok tersedia: $maxStockKg Kg", fontFamily = NunitoFont, fontSize = 15.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Kilogram (Kg)", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = subTextColor)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = buttonControlColor, modifier = Modifier.size(40.dp).focusProperties { canFocus = false }.clickable { focusManager.clearFocus(); val currentKg = kgInput.toIntOrNull() ?: 0; if (currentKg > 0) validateInput((currentKg - 1).toString(), gramInput) }) {
                                Box(contentAlignment = Alignment.Center) { Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = textColor) }
                            }
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.width(60.dp).padding(horizontal = 8.dp)) {
                                if (kgInput == "0" || kgInput.isEmpty()) {
                                    Text("0", style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Gray))
                                }
                                androidx.compose.foundation.text.BasicTextField(
                                    value = if (kgInput == "0") "" else kgInput,
                                    onValueChange = { input ->
                                        val filtered = input.filter { it.isDigit() }
                                        val finalValue = if (filtered.isEmpty()) "0" else filtered.toIntOrNull()?.toString() ?: "0"
                                        validateInput(finalValue, gramInput)
                                    },
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = if (errorMessage.contains("Kg", ignoreCase = true) || errorMessage.contains("stok", ignoreCase = true)) Color.Red else textColor),
                                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Surface(shape = CircleShape, color = VFreshPrimary, modifier = Modifier.size(40.dp).focusProperties { canFocus = false }.clickable { focusManager.clearFocus(); val currentKg = kgInput.toIntOrNull() ?: 0; validateInput((currentKg + 1).toString(), gramInput) }) {
                                Box(contentAlignment = Alignment.Center) { Text("+", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White) }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Gram (g)", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = subTextColor)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = buttonControlColor, modifier = Modifier.size(40.dp).focusProperties { canFocus = false }.clickable { focusManager.clearFocus(); val currentGram = gramInput.toIntOrNull() ?: 0; if (currentGram >= 50) validateInput(kgInput, (currentGram - 50).toString()) }) {
                                Box(contentAlignment = Alignment.Center) { Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = textColor) }
                            }
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.width(60.dp).padding(horizontal = 8.dp)) {
                                if (gramInput == "0" || gramInput.isEmpty()) {
                                    Text("0", style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Gray))
                                }
                                androidx.compose.foundation.text.BasicTextField(
                                    value = if (gramInput == "0") "" else gramInput,
                                    onValueChange = { input ->
                                        val filtered = input.filter { it.isDigit() }
                                        val finalValue = if (filtered.isEmpty()) "0" else filtered.toIntOrNull()?.toString() ?: "0"
                                        validateInput(kgInput, finalValue)
                                    },
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = if (errorMessage.contains("Gram", ignoreCase = true) || errorMessage.contains("stok", ignoreCase = true)) Color.Red else textColor),
                                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Surface(shape = CircleShape, color = VFreshPrimary, modifier = Modifier.size(40.dp).focusProperties { canFocus = false }.clickable { focusManager.clearFocus(); val currentGram = gramInput.toIntOrNull() ?: 0; validateInput(kgInput, (currentGram + 50).toString()) }) {
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
                            else if (errorMessage.isEmpty()) {
                                showBottomSheet = false
                                showSuccessOverlay = true // Panggil Overlay, gak langsung nutup layar!
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = if (isCartReady && errorMessage.isEmpty()) VFreshPrimary else (if (isDarkMode) Color(0xFF333333) else Color(0xFFEEEEEE))), shape = RoundedCornerShape(12.dp)
                    ) { Text(text = "Simpan ke Keranjang", fontFamily = RobotoFont, color = if (isCartReady && errorMessage.isEmpty()) Color.White else Color.Gray, fontWeight = FontWeight.Bold, fontSize = 16.sp) }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            if (!isCheckoutReady) { errorMessage = "Maaf, minimal checkout 5 Kg \n(Dapat dikombinasikan dengan jenis lainnya)" }
                            else if (errorMessage.isEmpty()) {
                                showBottomSheet = false
                                onCheckoutDirect("${kgInput}kg ${gramInput}g") // Lompat langsung ke Checkout!
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = if (isCheckoutReady && errorMessage.isEmpty()) VFreshPrimary else (if (isDarkMode) Color(0xFF444444) else Color(0xFFE0E0E0))), shape = RoundedCornerShape(12.dp)
                    ) { Text(text = "Checkout", fontFamily = RobotoFont, color = if (isCheckoutReady && errorMessage.isEmpty()) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 16.sp) }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    // Modal Overlay Centang Sukses
    if (showSuccessOverlay) {
        Dialog(onDismissRequest = {
            showSuccessOverlay = false
            onAddToCart("${kgInput}kg ${gramInput}g") // Eksekusi penambahan ke keranjang sesudah ditutup
        }) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = surfaceColor,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle, // Ikon Centang
                        contentDescription = "Success",
                        tint = VFreshPrimary,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Berhasil!", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = textColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Produk segar Anda berhasil dimasukkan ke keranjang belanja.", fontFamily = NunitoFont, fontSize = 15.sp, textAlign = TextAlign.Center, color = subTextColor)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            showSuccessOverlay = false
                            onAddToCart("${kgInput}kg ${gramInput}g") // Eksekusi penambahan ke keranjang kalau di klik OKE
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Oke", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

// ==========================================
// 8. KODE HALAMAN KERANJANG & FAVORIT
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartItems: MutableList<Pair<Product, String>>,
    onProductClick: (Product) -> Unit,
    onBack: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    isDarkMode: Boolean
) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray
    val negativeRed = Color(0xFFFF1E00)

    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }

    val checkedItems = remember { mutableStateMapOf<Int, Boolean>() }

    val filteredCart = cartItems.mapIndexed { index, pair -> index to pair }.filter {
        it.second.first.name.contains(searchQuery, ignoreCase = true)
    }

    val isAllSelected = filteredCart.isNotEmpty() && filteredCart.all { checkedItems[it.first] == true }

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
        Box(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp)) {
            Surface(shape = CircleShape, color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.size(44.dp).align(Alignment.CenterStart).clickable { onBack() }) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) }
            }
            Text("Keranjang", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.align(Alignment.Center))
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
            Column(modifier = Modifier.weight(1f).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.img_empty_cart), contentDescription = "Keranjang Kosong", modifier = Modifier.size(200.dp), contentScale = ContentScale.Fit)
                Spacer(modifier = Modifier.height(20.dp))
                Text("Halaman keranjang masih kosong nih,\nyuk tambahkan buah dan sayur segar!", fontFamily = NunitoFont, fontSize = 17.sp, color = subTextColor, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp), lineHeight = 24.sp)
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isAllSelected, onCheckedChange = { isChecked -> filteredCart.forEach { checkedItems[it.first] = isChecked } }, colors = CheckboxDefaults.colors(checkedColor = VFreshPrimary))
                Text("Pilih Semua", fontFamily = PoppinsFont, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = textColor, modifier = Modifier.weight(1f))
                if (checkedItems.values.any { it }) {
                    Text("Hapus Semua", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, color = negativeRed, fontSize = 15.sp, modifier = Modifier.clickable {
                        val itemsToRemove = checkedItems.filter { it.value }.keys.sortedDescending()
                        itemsToRemove.forEach { index -> cartItems.removeAt(index); checkedItems.remove(index) }
                    })
                }
            }

            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                items(filteredCart) { (originalIndex, pair) ->
                    val product = pair.first
                    val weight = pair.second
                    val kg = weight.substringBefore("kg").trim()
                    val g = weight.substringAfter("kg").substringBefore("g").trim()

                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), colors = CardDefaults.cardColors(containerColor = surfaceColor), elevation = CardDefaults.cardElevation(2.dp)) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = checkedItems[originalIndex] == true, onCheckedChange = { checkedItems[originalIndex] = it }, colors = CheckboxDefaults.colors(checkedColor = VFreshPrimary))

                            Image(painter = painterResource(id = product.imageRes), contentDescription = product.name, modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)).clickable { onProductClick(product) }, contentScale = ContentScale.Crop)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f).clickable { onProductClick(product) }) {
                                Text(product.name, fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColor)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("$kg Kg", fontFamily = FiraSansFont, fontSize = 15.sp, color = subTextColor)
                                Text("$g Gram", fontFamily = FiraSansFont, fontSize = 15.sp, color = subTextColor)
                            }
                            Text("Hapus", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, color = negativeRed, fontSize = 15.sp, modifier = Modifier.clickable { cartItems.removeAt(originalIndex); checkedItems.remove(originalIndex) })
                        }
                    }
                }
            }

            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 16.dp, color = surfaceColor) {
                Column(modifier = Modifier.padding(16.dp).navigationBarsPadding()) {
                    // LOGIKA REVISI: Peringatan merah HANYA MUNCUL kalau user milih barang > 0 gram TAPI belum mencapai 5000 gram.
                    if (totalGrams in 1..4999) {
                        Text("Maaf, minimal checkout 5 Kg (Pilih item lagi)", color = negativeRed, fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
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


// ==========================================
// KODE FAVORIT SCREEN
// ==========================================
@Composable
fun FavoriteScreen(favoriteItems: MutableList<Product>, onProductClick: (Product) -> Unit, onBack: () -> Unit, isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray
    val negativeRed = Color(0xFFFF1E00) // Warna merah khusus

    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }
    val checkedFavItems = remember { mutableStateMapOf<Int, Boolean>() }

    val filteredFavs = favoriteItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
    val isAllFavSelected = filteredFavs.isNotEmpty() && filteredFavs.all { checkedFavItems[it.id] == true }

    Column(modifier = Modifier.fillMaxSize().background(bgColor).clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }) {
        Box(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp)) {
            Surface(shape = CircleShape, color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.size(44.dp).align(Alignment.CenterStart).clickable { onBack() }) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) }
            }
            Text("Favorit", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 21.sp, color = textColor, modifier = Modifier.align(Alignment.Center))
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
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isAllFavSelected, onCheckedChange = { isChecked -> filteredFavs.forEach { checkedFavItems[it.id] = isChecked } }, colors = CheckboxDefaults.colors(checkedColor = VFreshPrimary))
                Text("Pilih Semua", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = textColor, modifier = Modifier.weight(1f))
                if (checkedFavItems.values.any { it }) {
                    Text("Hapus Semua", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, color = negativeRed, fontSize = 15.sp, modifier = Modifier.clickable {
                        favoriteItems.removeAll { checkedFavItems[it.id] == true }
                        checkedFavItems.clear()
                    })
                }
            }

            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredFavs) { product ->
                    Box {
                        ProductCard(product = product, onProductClick = { onProductClick(product) })

                        // Tatakan background putih di checkbox biar gak nyaru
                        Surface(shape = RoundedCornerShape(8.dp), color = Color.White, shadowElevation = 2.dp, modifier = Modifier.align(Alignment.TopStart).padding(8.dp)) {
                            Checkbox(checked = checkedFavItems[product.id] == true, onCheckedChange = { checkedFavItems[product.id] = it }, colors = CheckboxDefaults.colors(checkedColor = VFreshPrimary))
                        }

                        Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.9f), modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(36.dp).clickable { favoriteItems.remove(product) }) {
                            Icon(Icons.Filled.Favorite, contentDescription = "Hapus", tint = VFreshPrimary, modifier = Modifier.padding(6.dp))
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

    var isAnimated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isAnimated = true
    }

    Column(modifier = Modifier.fillMaxSize().background(bgColor)) {
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

        Spacer(modifier = Modifier.height(1.dp))

        Image(
            painter = painterResource(id = if (categoryName == "Buah") R.drawable.ic_cat_buah else R.drawable.ic_cat_sayur),
            contentDescription = categoryName,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .padding(if (isAnimated) 0.dp else 24.dp),
            contentScale = ContentScale.FillWidth
        )

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
// ==========================================
// 9. KODE CHECKOUT SCREEN
// ==========================================
// ==========================================
// 9. KODE CHECKOUT SCREEN (REVISI PAYMENT UI)
// ==========================================
@Composable
fun CheckoutScreen(onBack: () -> Unit, onNavigateToAddress: () -> Unit, isDarkMode: Boolean) {
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    var selectedMethodName by remember { mutableStateOf("Transfer Bank (BCA)") }

    // Struktur Grup Pembayaran Langsung Nampil!
    val paymentGroups = listOf(
        "Transfer Bank" to listOf("Bank BCA" to R.drawable.ic_pay_bca, "Bank Mandiri" to R.drawable.ic_pay_mandiri, "Bank BRI" to R.drawable.ic_pay_bri, "Bank BNI" to R.drawable.ic_pay_bni, "SeaBank" to R.drawable.ic_pay_seabank),
        "E-Wallet" to listOf("GoPay" to R.drawable.ic_pay_gopay, "OVO" to R.drawable.ic_pay_ovo, "DANA" to R.drawable.ic_pay_dana, "LinkAja" to R.drawable.ic_pay_linkaja, "ShopeePay" to R.drawable.ic_pay_shopeepay),
        "Agen" to listOf("Alfamart" to R.drawable.ic_pay_alfamart, "Alfamidi" to R.drawable.ic_pay_alfamidi, "Indomaret" to R.drawable.ic_pay_indomaret)
    )

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().height(56.dp).background(surfaceColor), contentAlignment = Alignment.Center) {
                IconButton(onClick = { onBack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = textColor)
                }
                Text("Checkout", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).background(if (isDarkMode) Color(0xFF121212) else Color(0xFFF5F5F5)).verticalScroll(rememberScrollState())) {

            // SEKSI ALAMAT
            Card(modifier = Modifier.fillMaxWidth().padding(15.dp).clickable { onNavigateToAddress() }, colors = CardDefaults.cardColors(containerColor = surfaceColor), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Text("Alamat Pengiriman", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColor)
                    Spacer(modifier = Modifier.height(11.dp))
                    Text("Belum ada alamat pengiriman terpilih.", fontFamily = NunitoFont, fontSize = 15.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(
                        onClick = { onNavigateToAddress() },
                        colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Atur Alamat Baru", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                    }
                }
            }

            // SEKSI METODE PEMBAYARAN LANGSUNG NAMPIL PER GRUP (GAK DI-UNHIDE SATU KOTAK)
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
                Text("Metode Pembayaran", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = textColor, modifier = Modifier.padding(bottom = 11.dp))

                paymentGroups.forEach { group ->
                    val groupName = group.first
                    val items = group.second
                    var isGroupExpanded by remember { mutableStateOf(false) }

                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 11.dp), colors = CardDefaults.cardColors(containerColor = surfaceColor), shape = RoundedCornerShape(12.dp)) {
                        Column {
                            // Header Grup (Bisa diklik buat expand/collapse)
                            Row(modifier = Modifier.fillMaxWidth().clickable { isGroupExpanded = !isGroupExpanded }.padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(groupName, fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = textColor)

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // PREVIEW IKON JIKA COLLAPSED
                                    if (!isGroupExpanded) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                            items.take(3).forEach { item ->
                                                Image(painter = painterResource(id = item.second), contentDescription = null, modifier = Modifier.size(24.dp).clip(RoundedCornerShape(4.dp)).background(Color.White))
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    Text(if (isGroupExpanded) "▲" else "▼", fontSize = 15.sp, color = Color.Gray)
                                }
                            }

                            // ISI GRUP JIKA EXPANDED (Ada bulatan hijau/RadioButton di kanan)
                            if (isGroupExpanded) {
                                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                                items.forEach { (name, iconRes) ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().clickable { selectedMethodName = name }.padding(horizontal = 15.dp, vertical = 11.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(painter = painterResource(id = iconRes), contentDescription = name, modifier = Modifier.width(50.dp).height(30.dp).clip(RoundedCornerShape(4.dp)).background(Color.White), contentScale = ContentScale.Fit)
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(name, fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = textColor, modifier = Modifier.weight(1f))

                                        // RADIO BUTTON (BULATAN HIJAU)
                                        RadioButton(selected = selectedMethodName == name, onClick = { selectedMethodName = name }, colors = RadioButtonDefaults.colors(selectedColor = VFreshPrimary))
                                    }
                                }
                            }
                        }
                    }
                }

                // QRIS SATUAN (Paling Bawah)
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 11.dp), colors = CardDefaults.cardColors(containerColor = surfaceColor), shape = RoundedCornerShape(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { selectedMethodName = "QRIS" }.padding(horizontal = 15.dp, vertical = 11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(painter = painterResource(id = R.drawable.ic_cat_buah), contentDescription = "QRIS", modifier = Modifier.width(50.dp).height(30.dp), contentScale = ContentScale.Fit)
                        Spacer(modifier = Modifier.width(15.dp))
                        Text("QRIS", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = textColor, modifier = Modifier.weight(1f))
                        RadioButton(selected = selectedMethodName == "QRIS", onClick = { selectedMethodName = "QRIS" }, colors = RadioButtonDefaults.colors(selectedColor = VFreshPrimary))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
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
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Lihat", tint = Color.Gray, modifier = Modifier.size(18.dp))
    }
}

// ==========================================
// 11. KODE ALAMAT PENGIRIMAN (ALUR BARU)
// ==========================================
@Composable
fun AddressListScreen(onBack: () -> Unit, onAddAddress: () -> Unit, isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().height(56.dp).background(surfaceColor), contentAlignment = Alignment.Center) {
                IconButton(onClick = { onBack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = textColor)
                }
                Text("Alamat Saya", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            }
        },
        bottomBar = {
            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 16.dp, color = surfaceColor) {
                Button(
                    onClick = { onAddAddress() },
                    colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding().height(48.dp)
                ) {
                    Text("+ Tambah Alamat Baru", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(bgColor), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(80.dp), tint = Color.LightGray)
                Spacer(modifier = Modifier.height(15.dp))
                Text("Belum ada alamat pengiriman", fontFamily = NunitoFont, fontSize = 17.sp, color = subTextColor, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressScreen(onBack: () -> Unit, isDarkMode: Boolean) {
    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    var labelAlamat by remember { mutableStateOf("Rumah") }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().height(56.dp).background(surfaceColor), contentAlignment = Alignment.Center) {
                IconButton(onClick = { onBack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = textColor)
                }
                Text("Tambah Alamat Baru", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = textColor)
            }
        },
        bottomBar = {
            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 16.dp, color = surfaceColor) {
                Button(
                    onClick = { onBack() },
                    colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding().height(48.dp)
                ) {
                    Text("Simpan", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).background(surfaceColor).verticalScroll(scrollState).clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Label Alamat
                Text("Label Alamat", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { labelAlamat = "Rumah" }, colors = ButtonDefaults.buttonColors(containerColor = if (labelAlamat == "Rumah") VFreshPrimary else Color(0xFFEEEEEE)), shape = RoundedCornerShape(8.dp)) {
                        Text("Rumah", color = if (labelAlamat == "Rumah") Color.White else Color.Gray)
                    }
                    Button(onClick = { labelAlamat = "Kantor" }, colors = ButtonDefaults.buttonColors(containerColor = if (labelAlamat == "Kantor") VFreshPrimary else Color(0xFFEEEEEE)), shape = RoundedCornerShape(8.dp)) {
                        Text("Kantor", color = if (labelAlamat == "Kantor") Color.White else Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                // Form Input Biasa
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nama Penerima", fontFamily = NunitoFont) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(11.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nomor Telepon/Whatsapp", fontFamily = NunitoFont) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(11.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nama Jalan, Gedung, Gang, Desa", fontFamily = NunitoFont) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(11.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Kecamatan, Kabupaten, Provinsi, Kode Pos", fontFamily = NunitoFont) }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(24.dp))

                // Ilustrasi Pin Maps Interaktif
                Text("Pin Maps", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth().height(180.dp).background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp)).border(1.dp, Color(0xFFC8E6C9), RoundedCornerShape(12.dp)).clickable { /* Simulasi Buka Peta */ }, contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Pin Maps", tint = VFreshPrimary, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Klik untuk atur lokasi peta", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = VFreshPrimary)
                    }
                }
                Spacer(modifier = Modifier.height(11.dp))
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = bgColor), shape = RoundedCornerShape(8.dp)) {
                    Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Lokasi Terpilih:", fontFamily = NunitoFont, fontSize = 15.sp, color = Color.Gray)
                            Text("Jl. Belum Diatur No. 0, Karawang", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = textColor)
                        }
                        Text("Ubah", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, color = VFreshPrimary, modifier = Modifier.clickable { /* Simulasi Ubah */ })
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}