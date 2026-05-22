package com.example.scanlegal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.scanlegal.ui.theme.*

private val DarkColorScheme = darkColorScheme(
    primary = LegalNavyDark,
    onPrimary = LegalNavyOnDark,
    secondary = SlateSecondaryDark,
    tertiary = AmberTertiaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = Color(0xFFE2E8F0),
    onSurface = Color(0xFFF8FAFC)
)

private val LightColorScheme = lightColorScheme(
    primary = LegalNavyLight,
    onPrimary = LegalNavyOnLight,
    secondary = SlateSecondaryLight,
    tertiary = AmberTertiaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF1E293B)
)
@Composable
fun ScanlegalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}