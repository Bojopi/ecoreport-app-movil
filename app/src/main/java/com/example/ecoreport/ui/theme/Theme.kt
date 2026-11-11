package com.example.ecoreport.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EcoColorScheme = lightColorScheme(
    primary = EcoGreen,
    primaryContainer = EcoGreenContainer,
    secondary = EcoSecondary,
    background = EcoBackground
)

@Composable
fun EcoReportTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EcoColorScheme,
        content = content
    )
}
