package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TableRow
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.CardTable
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Formatter
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle

@Composable
fun OrderViewInfo(order: Order) {
    SmallTitle(text = stringResource(R.string.orders_info_files))
    Spacer(modifier = Modifier.height(16.dp))

    CardTable(
        title = stringResource(R.string.orders_info_order),
        rows = listOf(
            TableRow(stringResource(R.string.orders_info_order_ordernumber), value = order.ordernumber_full),
            TableRow(stringResource(R.string.orders_info_order_status)) { DotText(text = order.status.name, color = order.status.color) },
            TableRow(stringResource(R.string.orders_info_order_store)) { DotText(text = order.store.name, color = order.store.color) },
            TableRow(stringResource(R.string.orders_info_order_payment)) { SimpleTextBold(text = stringResource(if (order.is_paid) R.string.orders_info_order_payment_paid else R.string.orders_info_order_payment_unpaid), color = if (order.is_paid) Color.Success.Regular else Color.Danger.Regular) },
            if (order.payment_method != null) TableRow(stringResource(R.string.orders_info_order_paymethod), value = order.payment_method) else null,
            TableRow(stringResource(R.string.orders_info_order_total_ex), value = Formatter.moneyFormat(order.total_ex))
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    CardTable(
        title = stringResource(R.string.orders_info_customer),
        rows = listOf(
            if (order.customer.delivery_address?.full_name != null ) TableRow(stringResource(R.string.name), value = order.customer.delivery_address.full_name) else null,
            if (order.customer.delivery_address?.email != null ) TableRow(stringResource(R.string.email), value = order.customer.delivery_address.email) else null,
            if (order.customer.delivery_address?.phone != null ) TableRow(stringResource(R.string.phone), value = order.customer.delivery_address.phone) else null,
            if (order.customer.delivery_address?.road != null ) TableRow(stringResource(R.string.road), value = "${order.customer.delivery_address.road} ${order.customer.delivery_address.house_number ?: ""}") else null,
            if (order.customer.delivery_address?.postcode != null ) TableRow(stringResource(R.string.zipcode), value = order.customer.delivery_address.postcode) else null,
            if (order.customer.delivery_address?.city != null ) TableRow(stringResource(R.string.city), value = order.customer.delivery_address.city) else null,
            if (order.customer.delivery_address?.state != null ) TableRow(stringResource(R.string.province), value = order.customer.delivery_address.state) else null,
            if (order.customer.delivery_address?.country != null ) TableRow(stringResource(R.string.country), value = order.customer.delivery_address.country) else null,
        )
    )
}