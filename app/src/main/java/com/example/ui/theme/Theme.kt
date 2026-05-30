package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DeepBlack = Color(0xFF0A0A0F)
val Charcoal = Color(0xFF111118)
val WhatsAppGreen = Color(0xFF25D366)
val NeonMint = Color(0xFF00FFB3)
val SoftGray = Color(0xFF888899)

private val DarkColorScheme = darkColorScheme(
    primary = WhatsAppGreen,
    secondary = NeonMint,
    tertiary = WhatsAppGreen,
    background = DeepBlack,
    surface = Charcoal,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Charcoal,
    onSurfaceVariant = SoftGray
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme, 
        typography = Typography,
        content = content
    )
}
