package com.example.practice1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.practice1.R

// KELUARGA FONT ROBOTO
val RobotoFont = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_semibold, FontWeight.SemiBold),
    Font(R.font.roboto_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

// KELUARGA FONT POPPINS
val PoppinsFont = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

// KELUARGA FONT FIRA SANS
val FiraSansFont = FontFamily(
    Font(R.font.firasans_regular, FontWeight.Normal),
    Font(R.font.firasans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.firasans_medium, FontWeight.Medium),
    Font(R.font.firasans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.firasans_semibold, FontWeight.SemiBold),
    Font(R.font.firasans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.firasans_bold, FontWeight.Bold),
    Font(R.font.firasans_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

// KELUARGA FONT NUNITO
val NunitoFont = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

// Setingan huruf bawaan aplikasi (Typography)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = PoppinsFont, // Kita jadikan Poppins sebagai font default
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)