package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun Spinner(
    size: Dp = 16.dp,
    color: androidx.compose.ui.graphics.Color = Color.White
) {
    CircularProgressIndicator(
        color = color,
        modifier = Modifier.size(size),
        strokeWidth = 2.dp
    )
}