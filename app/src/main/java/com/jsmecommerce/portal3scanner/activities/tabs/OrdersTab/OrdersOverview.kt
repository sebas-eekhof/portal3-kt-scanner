package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.jsmecommerce.portal3scanner.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.ui.components.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.SelectItem
import com.jsmecommerce.portal3scanner.models.general.OverviewStore
import com.jsmecommerce.portal3scanner.models.orders.OrderStatus
import com.jsmecommerce.portal3scanner.ui.components.form.Select
import com.jsmecommerce.portal3scanner.ui.components.general.ActionButton
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.screens.LoadingScreen
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.utils.Api
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.OrdersOverviewViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun OrdersOverview(nav: NavHostController, coreViewModel: CoreViewModel, uiViewModel: UiViewModel, ordersOverviewViewModel: OrdersOverviewViewModel = OrdersOverviewViewModel()) {
    var orders by remember { mutableStateOf<List<OverviewOrder>?>(null) }
    var stores by remember { mutableStateOf<List<OverviewStore>?>(null) }
    var statuses by remember { mutableStateOf<List<OrderStatus>?>(null) }

    var filterStatus by remember { mutableStateOf<String?>(null) }
    var filterStore by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    suspend fun refreshOrders() {
        withContext(Dispatchers.IO) {
            val req = Api.Request(context, "/orders")

            if(filterStatus != null)
                req.setQuery("status_id", filterStatus!!)

            if(filterStore != null)
                req.setQuery("store_id", filterStore!!)

            val res = req.exec()
            if(!res.hasError) {
                val jsonOrders = res.getJsonObject()?.getJSONArray("items")
                if(jsonOrders != null)
                    orders = OverviewOrder.fromJSONArray(jsonOrders)
            }
            withContext(Dispatchers.Main) { uiViewModel.setLoading(false) }
        }
    }

    LaunchedEffect(Unit) {
        uiViewModel.init(
            title = context.getString(R.string.orders_title),
            disableBack = true
        )
        uiViewModel.setActions {
            ActionButton(icon = R.drawable.ic_filter) {
                uiViewModel.setDrawer(
                    title = R.string.filters,
                    onClose = { CoroutineScope(Dispatchers.IO).launch { refreshOrders() } }
                ) {
                    if(statuses != null)
                        Select(
                            label = R.string.orders_info_order_status,
                            placeholder = R.string.orders_filter_all_statuses,
                            items = statuses!!.map {
                                SelectItem(name = it.id.toString()) {
                                    DotText(text = it.name, color = it.color)
                                }
                            },
                            value = filterStatus,
                            onChange = { filterStatus = it }
                        )
                    Spacer(modifier = Modifier.height(16.dp))
                    if(stores != null)
                        Select(
                            label = R.string.orders_info_order_store,
                            placeholder = R.string.orders_filter_all_stores,
                            items = stores!!.map {
                                SelectItem(name = it.id.toString()) {
                                    DotText(text = it.name, color = it.color)
                                }
                            },
                            value = filterStore,
                            onChange = { filterStore = it }
                        )
                }
            }
        }
        uiViewModel.setLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val resStatuses = Api.Request(context, "/orders/statusses")
                .exec()
            if(!resStatuses.hasError) {
                statuses = resStatuses.getJsonArray()?.let { OrderStatus.fromJSONArray(it) }
                filterStatus = statuses!!.firstOrNull { it.type == "new" }?.id.toString()
            }
            val resStores = Api.Request(context, "/stores/all")
                .exec()
            if(!resStores.hasError)
                stores = OverviewStore.fromJSONArray(resStores.getJsonArray()!!)
            refreshOrders()
        }
    }

    ScannerHost(nav = nav)

    if(orders != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),

        ) {
            orders!!.forEach { order ->
                Spacer(modifier = Modifier.height(8.dp))
                OverviewOrder(
                    order = order,
                    onClick = { nav.navigate("orders/view/${order.id}?title=${order.ordernumber_full}") }
                )
            }
        }
    } else
        LoadingScreen()
}