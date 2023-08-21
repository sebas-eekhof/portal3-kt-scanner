package com.jsmecommerce.portal3scanner.models

import androidx.compose.runtime.Composable

data class SelectItem(
    val name: String,
    val title: String? = null,
    val component: (@Composable () -> Unit)? = null
)
