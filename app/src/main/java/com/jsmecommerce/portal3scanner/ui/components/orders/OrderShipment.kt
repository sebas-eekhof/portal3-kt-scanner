package com.jsmecommerce.portal3scanner.ui.components.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.orders.OrderShipment
import com.jsmecommerce.portal3scanner.ui.components.general.AppIcon
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.theme.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderShipment(shipment: OrderShipment) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Surface(
            color = Color.Element,
            shape = RoundedCornerShape(4),
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { expanded = true }
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
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { SimpleText(text = "View") }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = { SimpleText(text = "Print") }, onClick = { /*TODO*/ })
        }
    }
}