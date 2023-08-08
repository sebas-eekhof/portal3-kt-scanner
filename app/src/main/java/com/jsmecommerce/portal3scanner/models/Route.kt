package com.jsmecommerce.portal3scanner.models

import androidx.compose.runtime.Composable
import com.jsmecommerce.portal3scanner.activities.MainActivity

class Route(val path: String? = null, val title: String? = null, val component: @Composable (activity: MainActivity) -> Unit) {
    @Composable
    fun render(activity: MainActivity) {
        component(activity)
    }
}