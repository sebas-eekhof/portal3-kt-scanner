package com.jsmecommerce.portal3scanner.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Permission(
    val permission: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)
