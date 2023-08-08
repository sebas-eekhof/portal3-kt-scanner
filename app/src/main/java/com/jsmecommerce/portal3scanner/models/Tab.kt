package com.jsmecommerce.portal3scanner.models

import androidx.annotation.DrawableRes
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.stores.NavigationStoreState

sealed class Tab(val title: String, @DrawableRes val icon: Int, val type: NavigationStoreState.TabType) {
    object DASHBOARD: Tab(title = "Dashboard", icon = R.drawable.ic_home, type = NavigationStoreState.TabType.DASHBOARD)
    object ORDERS: Tab(title = "Bestellingen", icon = R.drawable.ic_orders, type = NavigationStoreState.TabType.ORDERS)
    object SETTINGS: Tab(title = "Instellingen", icon = R.drawable.ic_settings, type = NavigationStoreState.TabType.SETTINGS)
}
