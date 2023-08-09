package com.jsmecommerce.portal3scanner.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DashboardShortcutHolder(
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
    val onClick: () -> Unit
)
