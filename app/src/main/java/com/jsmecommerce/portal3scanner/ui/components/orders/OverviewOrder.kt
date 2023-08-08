package com.jsmecommerce.portal3scanner.ui.components.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.Jdenticon
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun OverviewOrder(order: OverviewOrder) {
    Surface(
        color = Color.Element,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(6.dp)
        ) {
            Column {
                SmallTitle(order.ordernumber_full)
                Description(order.created_at)
            }
            Column {
                Row {
                    Jdenticon(order.customer.id.toString(), size = 48)
                    Description(order.customer.delivery_address?.full_name ?: "Onbekende klant")
                }
            }
        }
    }
}