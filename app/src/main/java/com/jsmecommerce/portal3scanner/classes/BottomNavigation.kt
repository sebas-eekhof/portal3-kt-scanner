package com.jsmecommerce.portal3scanner.classes

import androidx.annotation.DrawableRes
import com.jsmecommerce.portal3scanner.R

sealed class BottomNavigation(val route: String, val name: String, @DrawableRes val icon: Int) {
    object Dashboard: BottomNavigation("dashboard", "Dashboard", R.drawable.ic_home)
    object Orders: BottomNavigation("orders", "Bestellingen", R.drawable.ic_orders)
    object Settings: BottomNavigation("settings", "Instellingen", R.drawable.ic_settings)
}
