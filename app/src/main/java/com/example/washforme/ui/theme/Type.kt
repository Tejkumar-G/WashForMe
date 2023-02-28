package com.example.washforme.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.washforme.R


val font = FontFamily(
    listOf(
        Font(R.font.kalam_regular, FontWeight.Normal),
        Font(R.font.kalam_regular, FontWeight.Medium),
        Font(R.font.kalam_bold, FontWeight.Bold),
    )
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    h1 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h2 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)