package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.utils.Api
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun OrderView(nav: NavHostController, mvm: MainViewModel, orderId: Int, title: String) {
    var order by remember { mutableStateOf<Order?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mvm.init(
            title = title
        )
        CoroutineScope(Dispatchers.IO).launch {
            val res = Api.Request(context, "/orders/get")
                .setQuery("order_id", orderId.toString())
                .exec()
            if(!res.hasError) {
                val jsonRes = res.getJsonObject()
                if(jsonRes != null) {
                    order = Order.fromJSON(jsonRes)
                    withContext(Dispatchers.Main) {
                        mvm.setTitle(order!!.ordernumber_full ?: title)
                    }
                }
            }
        }
    }
    
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if(order == null)
            Description(text = "Loading order...")
        else {
            Description(text = "Order ${if (order!!.is_paid) "is paid" else "is not paid"}")
            Description(text = "Order customer name = ${order!!.customer.admin_address?.full_name ?: "Geen adres"}")
            order!!.rules.forEach { rule ->
                Description(text = "Rule ${rule.title}")
            }
        }
    }
}