package com.jsmecommerce.portal3scanner.models

import androidx.compose.runtime.Composable

data class TableRow(
    val name: String,
    val value: String? = null,
    val component: (@Composable () -> Unit)? = null
)
