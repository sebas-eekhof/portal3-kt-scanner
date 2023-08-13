package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.orders.OrderRule

@Composable
fun OrderViewScan(order: Order) {
    for(i in 0 until order.rules.count()) {
        if(i != 0)
            Spacer(modifier = Modifier.height(8.dp))
        OrderRule(rule = order.rules[i], rules = order.rules)
    }
}