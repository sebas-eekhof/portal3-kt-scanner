package com.jsmecommerce.portal3scanner.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class TabbarTab(
    val name: String,
    val component: @Composable () -> Unit
)
