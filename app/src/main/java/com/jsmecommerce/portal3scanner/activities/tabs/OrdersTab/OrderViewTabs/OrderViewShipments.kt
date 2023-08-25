package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.orders.OrderShipment
import com.jsmecommerce.portal3scanner.ui.components.screens.EmptyScreen

@Composable
fun OrderViewShipments(order: Order) {
    if(order.shipments.isEmpty())
        EmptyScreen()
    for(i in 0 until order.shipments.count()) {
        if(i != 0)
            Spacer(modifier = Modifier.height(8.dp))
        OrderShipment(shipment = order.shipments[i])
    }
}