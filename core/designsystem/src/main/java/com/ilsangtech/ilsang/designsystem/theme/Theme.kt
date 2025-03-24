package com.ilsangtech.ilsang.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun ILSANGTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ilsangColors,
        typography = ilsangTypography,
        content = content
    )
}