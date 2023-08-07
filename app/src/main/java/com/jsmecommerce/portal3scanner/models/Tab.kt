package com.jsmecommerce.portal3scanner.models

import androidx.annotation.DrawableRes
import com.jsmecommerce.portal3scanner.R

sealed class Tab(val title: String, @DrawableRes val icon: Int, val name: String) {
    object DASHBOARD: Tab(title = "Dashboard", icon = R.drawable.ic_home, name = "dashboard")
    object ORDERS: Tab(title = "Bestellingen", icon = R.drawable.ic_orders, name = "orders")
    object SETTINGS: Tab(title = "Instellingen", icon = R.drawable.ic_settings, name = "settings")
}
