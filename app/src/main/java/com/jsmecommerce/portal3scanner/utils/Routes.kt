package com.jsmecommerce.portal3scanner.utils

import com.jsmecommerce.portal3scanner.activities.tabs.DashboardTab
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrdersOverview
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsInformation
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsOverview
import com.jsmecommerce.portal3scanner.models.Route
import com.jsmecommerce.portal3scanner.stores.NavigationStoreState

fun getRoutes(): HashMap<NavigationStoreState.TabType, List<Route>> {
    val map = HashMap<NavigationStoreState.TabType, List<Route>>()
    map[NavigationStoreState.TabType.DASHBOARD] = listOf(
        Route(title = "Dashboard", component = { DashboardTab() })
    )
    map[NavigationStoreState.TabType.ORDERS] = listOf(
        Route(title = "Bestellingen", component = { OrdersOverview() })
    )
    map[NavigationStoreState.TabType.SETTINGS] = listOf(
        Route(title = "Instellingen", component = { SettingsOverview() }),
        Route(path = "information", title = "Information", component = { SettingsInformation() })
    )
    return map
}