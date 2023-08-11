package com.jsmecommerce.portal3scanner.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class TabbarTab(
    @StringRes val name: Int,
    val component: @Composable () -> Unit
)
