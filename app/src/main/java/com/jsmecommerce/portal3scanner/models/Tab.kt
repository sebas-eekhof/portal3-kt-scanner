package com.jsmecommerce.portal3scanner.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jsmecommerce.portal3scanner.R

sealed class Tab(@StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {
    object DASHBOARD: Tab(title = R.string.dashboard_title, icon = R.drawable.ic_home, route = "dashboard")
    object ORDERS: Tab(title = R.string.orders_title, icon = R.drawable.ic_orders, route = "orders")
    object SETTINGS: Tab(title = R.string.settings_title, icon = R.drawable.ic_settings, route = "settings")
}
