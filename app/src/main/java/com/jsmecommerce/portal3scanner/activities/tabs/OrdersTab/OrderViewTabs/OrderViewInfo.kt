package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TableRow
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.CardTable
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Formatter

@Composable
fun OrderViewInfo(order: Order) {
    CardTable(
        title = "Informatie",
        rows = listOf(
            TableRow("Bestelnummer", value = order.ordernumber_full),
            TableRow("Status") { DotText(text = order.status.name, color = order.status.color) },
            TableRow("Webshop") { DotText(text = order.store.name, color = order.store.color) },
            TableRow("Betaling") { SimpleTextBold(text = if (order.is_paid) "Betaald" else "Niet betaald", color = if (order.is_paid) Color.Success.Regular else Color.Danger.Regular) },
            if (order.payment_method != null) TableRow("Betaalmethode", value = order.payment_method) else null,
            TableRow("Totaal excl.", value = Formatter.moneyFormat(order.total_ex))
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    CardTable(
        title = "Klant",
        rows = listOf(
            if (order.customer.delivery_address?.full_name != null ) TableRow("Naam", value = order.customer.delivery_address.full_name) else null,
            if (order.customer.delivery_address?.email != null ) TableRow("Email", value = order.customer.delivery_address.email) else null,
            if (order.customer.delivery_address?.phone != null ) TableRow("Telefoonnummer", value = order.customer.delivery_address.phone) else null,
            if (order.customer.delivery_address?.road != null ) TableRow("Straat", value = "${order.customer.delivery_address.road} ${order.customer.delivery_address.house_number ?: ""}") else null,
            if (order.customer.delivery_address?.postcode != null ) TableRow("Postcode", value = order.customer.delivery_address.postcode) else null,
            if (order.customer.delivery_address?.city != null ) TableRow("Stad", value = order.customer.delivery_address.city) else null,
            if (order.customer.delivery_address?.state != null ) TableRow("Provincie", value = order.customer.delivery_address.state) else null,
            if (order.customer.delivery_address?.country != null ) TableRow("Land", value = order.customer.delivery_address.country) else null,
        )
    )
}