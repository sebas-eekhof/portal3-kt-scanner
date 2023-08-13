package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs.OrderViewInfo
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs.OrderViewScan
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs.OrderViewShipments
import com.jsmecommerce.portal3scanner.models.TabbarTab
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.Tabbar
import com.jsmecommerce.portal3scanner.ui.components.screens.LoadingScreen
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

    if(order == null)
        LoadingScreen()
    else
        Tabbar(
            tabs = listOf(
                TabbarTab(stringResource(id = R.string.orders_view_info)) {
                    OrderViewInfo(order = order!!)
                },
                TabbarTab(stringResource(id = R.string.orders_view_scan)) {
                    OrderViewScan(order = order!!)
                },
                TabbarTab(String.format(stringResource(id = R.string.orders_view_shipments), order!!.shipments.count())) {
                    OrderViewShipments(order = order!!)
                }
            ),
            defaultTab = 1
        )
}