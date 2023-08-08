package com.jsmecommerce.portal3scanner.models

import androidx.compose.runtime.Composable

class Route(val path: String? = null, val title: String? = null, val component: @Composable () -> Unit) {
    @Composable
    fun render() {
        component()
    }
}