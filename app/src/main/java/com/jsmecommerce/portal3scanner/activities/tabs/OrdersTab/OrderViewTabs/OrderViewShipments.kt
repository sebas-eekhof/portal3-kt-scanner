package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.AppIcon
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.components.screens.EmptyScreen
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun OrderViewShipments(order: Order) {
    if(order.shipments.isEmpty())
        EmptyScreen()
    for(i in 0 until order.shipments.count()) {
        val shipment = order.shipments[i]
        Surface(
            color = Color.Element,
            shape = RoundedCornerShape(4),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(shipment.app != null) {
                    AppIcon(
                        identifier = shipment.app.identifier,
                        icon = shipment.app.icon ?: "icon.webp"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Column {
                    shipment.barcode?.let { SmallTitle(it) }
                    if(shipment.barcode != null && shipment.description != null)
                        Spacer(modifier = Modifier.height(2.dp))
                    shipment.description?.let { Description(it) }
                }
            }
        }
    }
}