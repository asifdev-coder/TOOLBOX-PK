package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ToolBoxColorScheme = darkColorScheme(
  primary = PrimaryAccent,
  onPrimary = BackgroundDark,
  primaryContainer = SecondaryBackground,
  onPrimaryContainer = PrimaryAccentGlow,
  secondary = PrimaryAccent,
  onSecondary = BackgroundDark,
  secondaryContainer = CardBackground,
  onSecondaryContainer = TextPrimary,
  tertiary = StatusInfo,
  onTertiary = BackgroundDark,
  background = BackgroundDark,
  onBackground = TextPrimary,
  surface = CardBackground,
  onSurface = TextPrimary,
  surfaceVariant = SecondaryBackground,
  onSurfaceVariant = TextSecondary,
  outline = BorderGreen,
  outlineVariant = BorderGreenHover,
  error = StatusError,
  onError = TextPrimary
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = ToolBoxColorScheme,
    typography = ToolBoxTypography,
    content = content
  )
}
