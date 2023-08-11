package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TableRow
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.CardTable
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.InfoRow
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun OrderViewInfo(order: Order) {
    CardTable(
        title = "Informatie",
        rows = listOf(
            TableRow("Bestelnummer", value = order.ordernumber_full),
            TableRow("Status") { DotText(text = order.status.name, color = order.status.color) },
            TableRow("Webshop") { DotText(text = order.store.name, color = order.store.color) },
            TableRow("Betaling") { SimpleTextBold(text = if (order.is_paid) "Betaald" else "Niet betaald", color = if (order.is_paid) Color.Success.Regular else Color.Danger.Regular) },
        )
    )
}