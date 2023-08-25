package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TableRow
import com.jsmecommerce.portal3scanner.ui.components.general.CardTable
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Formatter
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle

@Composable
fun OrderViewInfo(order: Order) {
    SmallTitle(text = stringResource(R.string.orders_info_files))
    Spacer(modifier = Modifier.height(16.dp))

    CardTable(
        title = stringResource(R.string.orders_info_order),
        rows = listOf(
            TableRow(stringResource(R.string.orders_info_order_ordernumber), value = order.orderNumberFull),
            TableRow(stringResource(R.string.orders_info_order_status)) { DotText(text = order.status.name, color = order.status.color) },
            TableRow(stringResource(R.string.orders_info_order_store)) { DotText(text = order.store.name, color = order.store.color) },
            TableRow(stringResource(R.string.orders_info_order_payment)) { SimpleTextBold(text = stringResource(if (order.isPaid) R.string.orders_info_order_payment_paid else R.string.orders_info_order_payment_unpaid), color = if (order.isPaid) Color.Success.Regular else Color.Danger.Regular) },
            if (order.paymentMethod != null) TableRow(stringResource(R.string.orders_info_order_paymethod), value = order.paymentMethod) else null,
            TableRow(stringResource(R.string.orders_info_order_total_ex), value = Formatter.moneyFormat(order.totalEx))
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    CardTable(
        title = stringResource(R.string.orders_info_customer),
        rows = listOf(
            if (order.customer.deliveryAddress?.fullName != null ) TableRow(stringResource(R.string.name), value = order.customer.deliveryAddress.fullName) else null,
            if (order.customer.deliveryAddress?.email != null ) TableRow(stringResource(R.string.email), value = order.customer.deliveryAddress.email) else null,
            if (order.customer.deliveryAddress?.phone != null ) TableRow(stringResource(R.string.phone), value = order.customer.deliveryAddress.phone) else null,
            if (order.customer.deliveryAddress?.road != null ) TableRow(stringResource(R.string.road), value = "${order.customer.deliveryAddress.road} ${order.customer.deliveryAddress.houseNumber ?: ""}") else null,
            if (order.customer.deliveryAddress?.postcode != null ) TableRow(stringResource(R.string.zipcode), value = order.customer.deliveryAddress.postcode) else null,
            if (order.customer.deliveryAddress?.city != null ) TableRow(stringResource(R.string.city), value = order.customer.deliveryAddress.city) else null,
            if (order.customer.deliveryAddress?.state != null ) TableRow(stringResource(R.string.province), value = order.customer.deliveryAddress.state) else null,
            if (order.customer.deliveryAddress?.country != null ) TableRow(stringResource(R.string.country), value = order.customer.deliveryAddress.country) else null,
        )
    )
}