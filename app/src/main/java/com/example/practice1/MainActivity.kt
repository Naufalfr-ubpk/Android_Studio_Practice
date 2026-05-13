package com.example.practice1

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
// 1. DATA PRODUK (DATABASE LOKAL SEMENTARA)
// ==========================================
data class Product(val id: Int, val name: String, val price: String, val imageRes: Int)

val dummyProducts = listOf(
    Product(1, "Kentang", "15.000", R.drawable.img_item1),
    Product(2, "Kol", "8.000", R.drawable.img_item2),
    Product(3, "Sawi Hijau", "10.000", R.drawable.img_item3),
    Product(4, "Tomat", "15.000", R.drawable.img_item4),
    Product(5, "Wortel", "12.000", R.drawable.img_item5),
    Product(6, "Apel", "30.000", R.drawable.img_item6),
    Product(7, "Jeruk", "25.000", R.drawable.img_item7),
    Product(8, "Mangga", "20.000", R.drawable.img_item8),
    Product(9, "Pepaya", "8.000", R.drawable.img_item9),
    Product(10, "Pisang", "15.000", R.drawable.img_item10)
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
        modifier = Modifier.fillMaxSize().background(Color.White).imePadding()
            .clickable(indication = null, interactionSource = interactionSource) { focusManager.clearFocus() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
                .verticalScroll(scrollState).padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(painter = painterResource(id = R.drawable.img_auth), contentDescription = "Ilustrasi Login/Register", modifier = Modifier.fillMaxWidth().height(180.dp), contentScale = ContentScale.Fit)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = if (isLogin) "Masuk ke Akun" else "Daftar Akun Baru", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color.Black, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = if (isLogin) "Selamat datang kembali di V-Fresh!" else "Lengkapi data di bawah untuk bergabung", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color(0xFF5D5D5D), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 15.dp), textAlign = TextAlign.Center, lineHeight = 25.sp)
            }

            OutlinedTextField(value = email, onValueChange = { email = it; emailError = false; errorMessage = "" }, isError = emailError, label = { Text("Email", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp) }, leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email", tint = VFreshPrimary) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VFreshPrimary, focusedLabelColor = VFreshPrimary, cursorColor = VFreshPrimary), singleLine = true)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = password, onValueChange = { password = it; passwordError = false; errorMessage = "" }, isError = passwordError, label = { Text("Password", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp) }, leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock", tint = VFreshPrimary) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VFreshPrimary, focusedLabelColor = VFreshPrimary, cursorColor = VFreshPrimary), singleLine = true)

            if (!isLogin) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it; confirmPasswordError = false; errorMessage = "" }, isError = confirmPasswordError, label = { Text("Konfirmasi Password", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp) }, leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Lock", tint = VFreshPrimary) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VFreshPrimary, focusedLabelColor = VFreshPrimary, cursorColor = VFreshPrimary), singleLine = true)
            }

            if (isLogin) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) { Text(text = "Lupa Password?", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, color = VFreshDark, fontSize = 17.sp, modifier = Modifier.clickable {  }) }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    emailError = false; passwordError = false; confirmPasswordError = false; errorMessage = ""
                    if (email.isBlank() && password.isBlank()) { emailError = true; passwordError = true; errorMessage = "Email dan Password tidak\nboleh kosong"
                    } else if (email.isBlank()) { emailError = true; errorMessage = "Email tidak boleh kosong"
                    } else if (password.isBlank()) { passwordError = true; errorMessage = "Password tidak boleh kosong"
                    } else if (!isValidPassword(password)) { passwordError = true; errorMessage = "Password min. 6 karakter, harus\nmenggunakan kecil, angka,\ndan simbol"
                    } else if (!isLogin && password != confirmPassword) { confirmPasswordError = true; errorMessage = "Konfirmasi password tidak cocok"
                    } else { onLoginSuccess() }
                },
                modifier = Modifier.fillMaxWidth(0.85f).height(56.dp).shadow(elevation = 3.dp, shape = CircleShape, ambientColor = Color.Black.copy(alpha = 0.6f), spotColor = Color.Black.copy(alpha = 0.9f)),
                colors = ButtonDefaults.buttonColors(containerColor = VFreshPrimary), shape = CircleShape
            ) { Text(text = if (isLogin) "Masuk" else "Daftar Sekarang", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White) }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(onClick = { onLoginSuccess() }, shape = CircleShape, color = Color.White, shadowElevation = 3.dp, modifier = Modifier.fillMaxWidth(0.85f).height(56.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Logo Google", modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = if (isLogin) "Masuk dengan Google" else "Daftar dengan Google", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)) {
                Text(text = if (isLogin) "Belum punya akun? " else "Sudah punya akun? ", fontFamily = NunitoFont, color = Color.Gray, fontSize = 17.sp)
                Text(text = if (isLogin) "Daftar" else "Masuk", fontFamily = RobotoFont, fontWeight = FontWeight.Bold, color = VFreshPrimary, fontSize = 17.sp, modifier = Modifier.clickable { isLogin = !isLogin; email = ""; password = ""; confirmPassword = ""; emailError = false; passwordError = false; confirmPasswordError = false; errorMessage = "" })
            }
        }
    }
}

// ==========================================
// 4. KODE MAIN SCREEN & HOME SCREEN
// ==========================================
@Composable
fun MainScreen(onLogout: () -> Unit, isDarkMode: Boolean, onThemeChange: (Boolean) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val bgColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF9F9F9)
    val bottomBarColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val indicatorColor = if (isDarkMode) Color(0xFF2C2C2C) else Color(0xFFF2FCEB)
    val textColor = if (isDarkMode) Color.White else VFreshDark

    if (selectedProduct != null) {
        ProductDetailScreen(
            product = selectedProduct!!,
            onBack = { selectedProduct = null },
            isDarkMode = isDarkMode
        )
    } else {
        Scaffold(
            bottomBar = {

                // Slide 5: Bottom Navigation View
                NavigationBar(containerColor = bottomBarColor, tonalElevation = 8.dp) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                // ============================================

                        onClick = { selectedTab = 0 }, icon = { Icon(Icons.Default.Home, contentDescription = "Beranda", modifier = Modifier.size(30.dp)) }, label = { Text("Beranda", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                    NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifikasi", modifier = Modifier.size(30.dp)) }, label = { Text("Notifikasi", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                    NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2 }, icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Pesanan", modifier = Modifier.size(30.dp)) }, label = { Text("Pesanan", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                    NavigationBarItem(selected = selectedTab == 3, onClick = { selectedTab = 3 }, icon = { Icon(Icons.Default.Person, contentDescription = "Akun", modifier = Modifier.size(30.dp)) }, label = { Text("Akun", fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = VFreshPrimary, selectedTextColor = VFreshPrimary, unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray, indicatorColor = indicatorColor))
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(bgColor)) {
                when (selectedTab) {
                    0 -> HomeScreen(onProductClick = { product -> selectedProduct = product }, isDarkMode = isDarkMode, onThemeChange = onThemeChange)
                    1 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Halaman Notifikasi", fontFamily = PoppinsFont, color = textColor) }
                    2 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Halaman Pesanan", fontFamily = PoppinsFont, color = textColor) }
                    3 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Ini Halaman Akun", fontFamily = PoppinsFont, color = textColor)
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) { Text("Logout", color = Color.White) }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 4. KODE HOME SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(onProductClick: (Product) -> Unit, isDarkMode: Boolean, onThemeChange: (Boolean) -> Unit) {
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

    // Dynamic Colors untuk Dark Mode
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val searchBarColor = if (isDarkMode) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val textColorDark = if (isDarkMode) Color.White else VFreshDark

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {
        // 1. HEADER
        Surface(color = surfaceColor, shadowElevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 14.dp)
                    .offset(y = (-4).dp)
                    .fillMaxWidth()
            ) {
                // IKON FAVORIT
                IconButton(onClick = { /* TODO Favorit */ }, modifier = Modifier.size(40.dp)) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorit",
                        tint = VFreshPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // SEARCH BAR CUSTOM
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

                // IKON KERANJANG
                IconButton(onClick = { /* TODO Cart */ }, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Keranjang", tint = VFreshPrimary, modifier = Modifier.size(28.dp))
                }

                // SWITCH COMPAT (DARK MODE TOGGLE) - DIBUNGKUS BOX BUAT SHADOW LABEL
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    // Trik Latar Belakang (Fake Track) buat ngasih Shadow ke Label
                    if (!isDarkMode) {
                        Surface(
                            modifier = Modifier
                                .width(54.dp) // Dilebarin dikit menyesuaikan panjang asli Switch
                                .height(32.dp),
                            shape = CircleShape,
                            shadowElevation = 4.dp,
                            color = Color.White
                        ) {}
                    }

                    // Slide 2: Switch Compat
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onThemeChange(it) },
                        modifier = Modifier, // Hapus scale di sini karena udah di Box Parent
                        thumbContent = {
                        // =======================================

                            // TOMBOL (Thumb) - UDAH ADA SHADOW 3.dp!
                            Box(
                                modifier = Modifier
                                    .size(SwitchDefaults.IconSize)
                                    .shadow(elevation = 3.dp, shape = CircleShape)
                                    .background(if (isDarkMode) Color(0xFF2C2C2C) else Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDarkMode) {
                                    Icon(
                                        imageVector = Icons.Default.DarkMode,
                                        contentDescription = "Bulan",
                                        tint = Color(0xFFE0B0FF),
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
                            checkedTrackColor = Color(0xFF4B0082).copy(alpha = 0.5f),
                            uncheckedTrackColor = Color.Transparent, // Dibikin transparan nembus ke fake track putih
                            uncheckedBorderColor = Color.Transparent,
                            checkedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }

        // BAGIAN KONTEN BISA DI-SCROLL
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            // CAROUSEL BANNER
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

            // KATEGORI FILTER
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
                            modifier = Modifier.fillMaxWidth().clickable { /* TODO Filter Sayur */ }
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
                            modifier = Modifier.fillMaxWidth().clickable { /* TODO Filter Buah */ }
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

            // TEKS REKOMENDASI
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

            // LIST PRODUK ATAU PERINGATAN KOSONG
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
                        onProductClick = { onProductClick(product) },
                        isDarkMode = isDarkMode
                    )
                }
            }
        }
    }
}

// ==========================================
// 5. KODE PRODUCT CARD (KOTAK PRODUK DI BERANDA)
// ==========================================
@Composable
fun ProductCard(
    product: Product,
    onProductClick: () -> Unit,
    isDarkMode: Boolean
) {
    val cardColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black

    // Slide 3: Ripple Effects
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        // =========================================

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
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
                Text(text = product.name, fontFamily = PoppinsFont, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = textColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
// 6. KODE DETAIL SCREEN & BOTTOM SHEET
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(product: Product, onBack: () -> Unit, isDarkMode: Boolean) {
    val context = LocalContext.current

    val itemColors = mapOf(
        "Kentang" to Color(0xFFF4D4A5),
        "Kol" to Color(0xFFB4E179),
        "Sawi Hijau" to Color(0xFFACCE43),
        "Tomat" to Color(0xFFD1806B),
        "Wortel" to Color(0xFFFF9B72),
        "Apel" to Color(0xFFD96679),
        "Jeruk" to Color(0xFFFFBA4A),
        "Mangga" to Color(0xFF97B360),
        "Pepaya" to Color(0xFFDDB256),
        "Pisang" to Color(0xFFF5D791)
    )

    val headerBackgroundColor = itemColors[product.name] ?: Color(0xFFE8F5E9)

    BackHandler { onBack() }

    // ==========================================
    // Slide 1: Extract Colour Palette
    // ==========================================
    var vibrantColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(product.imageRes) {
        val bitmap = BitmapFactory.decodeResource(context.resources, product.imageRes)
        if (bitmap != null) {
            Palette.from(bitmap).generate { palette ->
                palette?.vibrantSwatch?.let { vibrantColor = Color(it.rgb) }
            }
        }
    }
    // ==========================================

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
        kgInput = newKg
        gramInput = newGram
        errorMessage = ""

        val kgValue = newKg.toIntOrNull()
        val gramValue = newGram.toIntOrNull()

        if (newKg.isNotEmpty() && kgValue == null) {
            errorMessage = "Maaf, Kg harus berupa angka bulat"
            return
        }
        if (newGram.isNotEmpty() && gramValue == null) {
            errorMessage = "Maaf, Gram harus berupa angka bulat"
            return
        }

        val safeKg = kgValue ?: 0
        val safeGram = gramValue ?: 0
        val totalWeightInKg = safeKg + (safeGram / 1000.0)

        if (safeGram % 50 != 0) {
            errorMessage = "Maaf, Gram harus kelipatan 50"
            return
        }
        if (totalWeightInKg > maxStockKg) {
            errorMessage = "Maaf, stok hanya tersedia $maxStockKg Kg"
            return
        }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                headerBackgroundColor,
                                headerBackgroundColor.copy(alpha = 0.5f),
                                bgColor
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = surfaceColor,
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(40.dp).clickable { onBack() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.padding(8.dp), tint = textColor)
                    }
                }

                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 32.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.name,
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            color = textColor
                        )
                        Text(
                            text = "Rp ${product.price} / 1 kg",
                            color = VFreshPrimary,
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Slide 4: Rating Bar
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= userRating) Icons.Filled.Star else Icons.Outlined.Star,
                    // ================================================

                                contentDescription = "Star $i",
                                tint = if (i <= userRating) Color(0xFFFFD700) else Color.LightGray,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable { userRating = i }
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "4.8 (120 Ulasan)", fontFamily = NunitoFont, color = Color.Gray, fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Deskripsi", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${product.name} segar berkualitas tinggi, langsung dipanen dari petani lokal. Cocok untuk memenuhi kebutuhan nutrisi keluarga Anda sehari-hari dengan kesegaran yang terjamin.",
                        fontFamily = NunitoFont,
                        fontSize = 15.sp,
                        color = subTextColor,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        colors = CardDefaults.cardColors(containerColor = infoCardColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Outlined.Info, contentDescription = "Info", tint = VFreshPrimary, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Syarat Checkout", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = textColor)
                                Text("Pesanan baru dapat diproses (checkout) jika total kuantitas belanja mencapai minimal 5 Kg.", fontFamily = NunitoFont, fontSize = 14.sp, color = subTextColor)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() + 20.dp))
                }
            }
        }
    }

    // Slide 6: Bottom Sheet View
    if (showBottomSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val focusManager = LocalFocusManager.current

        val totalGrams = ((kgInput.toIntOrNull() ?: 0) * 1000) + (gramInput.toIntOrNull() ?: 0)
        val isCheckoutReady = totalGrams >= 5000
        val isCartReady = totalGrams > 0

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = surfaceColor
        ) {
        // ========================================================

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        focusManager.clearFocus()
                    }
            ) {
                Text("Pilih Kuantitas", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = textColor)
                Text("Stok tersedia: $maxStockKg Kg", fontFamily = NunitoFont, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Kilogram (Kg)", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = subTextColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = buttonControlColor, modifier = Modifier.size(40.dp).clickable {
                            val currentKg = kgInput.toIntOrNull() ?: 0
                            if (currentKg > 0) validateInput((currentKg - 1).toString(), gramInput)
                        }) {
                            Box(contentAlignment = Alignment.Center) { Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = textColor) }
                        }

                        androidx.compose.foundation.text.BasicTextField(
                            value = kgInput,
                            onValueChange = { validateInput(it, gramInput) },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                                color = if (errorMessage.contains("Kg", ignoreCase = true) || errorMessage.contains("stok", ignoreCase = true)) Color.Red else textColor
                            ),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                            modifier = Modifier.width(60.dp).padding(horizontal = 8.dp)
                        )

                        Surface(shape = CircleShape, color = VFreshPrimary, modifier = Modifier.size(40.dp).clickable {
                            val currentKg = kgInput.toIntOrNull() ?: 0
                            validateInput((currentKg + 1).toString(), gramInput)
                        }) {
                            Box(contentAlignment = Alignment.Center) { Text("+", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Gram (g)", fontFamily = NunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = subTextColor)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = buttonControlColor, modifier = Modifier.size(40.dp).clickable {
                            val currentGram = gramInput.toIntOrNull() ?: 0
                            if (currentGram >= 50) validateInput(kgInput, (currentGram - 50).toString())
                        }) {
                            Box(contentAlignment = Alignment.Center) { Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = textColor) }
                        }

                        androidx.compose.foundation.text.BasicTextField(
                            value = gramInput,
                            onValueChange = { validateInput(kgInput, it) },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                                color = if (errorMessage.contains("Gram", ignoreCase = true) || errorMessage.contains("stok", ignoreCase = true)) Color.Red else textColor
                            ),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                            modifier = Modifier.width(60.dp).padding(horizontal = 8.dp)
                        )

                        Surface(shape = CircleShape, color = VFreshPrimary, modifier = Modifier.size(40.dp).clickable {
                            val currentGram = gramInput.toIntOrNull() ?: 0
                            validateInput(kgInput, (currentGram + 50).toString())
                        }) {
                            Box(contentAlignment = Alignment.Center) { Text("+", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White) }
                        }
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = errorMessage, color = Color.Red, fontFamily = NunitoFont, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (totalGrams == 0) {
                            errorMessage = "Maaf, Kuantitas tidak boleh kosong"
                        } else if (errorMessage.isEmpty()) {
                            showBottomSheet = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCartReady && errorMessage.isEmpty()) VFreshPrimary else (if (isDarkMode) Color(0xFF333333) else Color(0xFFEEEEEE))
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Simpan ke Keranjang",
                        fontFamily = RobotoFont,
                        color = if (isCartReady && errorMessage.isEmpty()) Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (!isCheckoutReady) {
                            errorMessage = "Maaf, minimal checkout 5 Kg"
                        } else if (errorMessage.isEmpty()) {
                            showBottomSheet = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCheckoutReady && errorMessage.isEmpty()) VFreshPrimary else (if (isDarkMode) Color(0xFF444444) else Color(0xFFE0E0E0))
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Checkout",
                        fontFamily = RobotoFont,
                        color = if (isCheckoutReady && errorMessage.isEmpty()) Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}