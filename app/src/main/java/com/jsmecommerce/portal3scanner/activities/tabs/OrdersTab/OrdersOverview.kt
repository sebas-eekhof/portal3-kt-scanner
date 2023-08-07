package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.utils.AppController

@Composable
fun OrdersOverview(appController: AppController) {
    appController.setPage("Bestellingen")

    Column {
        SimpleText("orders")
    }
}