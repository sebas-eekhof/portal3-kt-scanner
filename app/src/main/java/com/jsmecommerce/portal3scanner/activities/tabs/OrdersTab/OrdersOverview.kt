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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.SelectItem
import com.jsmecommerce.portal3scanner.ui.components.form.Select
import com.jsmecommerce.portal3scanner.ui.components.general.ActionButton
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.ui.components.screens.LoadingScreen
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.OrdersOverviewViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OrdersOverview(nav: NavHostController, coreViewModel: CoreViewModel, uiViewModel: UiViewModel) {
    val vm: OrdersOverviewViewModel = viewModel(factory = OrdersOverviewViewModel.Factory(LocalContext.current, uiViewModel))
    val orders by vm.orders.observeAsState(null)
    val stores by vm.stores.observeAsState(null)
    val statuses by vm.statuses.observeAsState(null)

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiViewModel.init(
            title = context.getString(R.string.orders_title),
            disableBack = true
        )
        uiViewModel.setActions {
            ActionButton(icon = R.drawable.ic_filter) {
                uiViewModel.setDrawer(
                    title = R.string.filters,
                    onClose = { CoroutineScope(Dispatchers.IO).launch { vm.refreshOrders() } }
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
                            value = vm.filterStatusId,
                            onChange = { vm.setFilterStatusId(it) }
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
                            value = vm.filterStoreId,
                            onChange = { vm.setFilterStoreId(it) }
                        )
                }
            }
        }
        vm.init()
    }

    ScannerHost(nav = nav)

    if(orders != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),

        ) {
            orders!!.items.forEach { order ->
                Spacer(modifier = Modifier.height(8.dp))
                OverviewOrder(
                    order = order,
                    onClick = { nav.navigate("orders/view/${order.id}?title=${order.orderNumberFull}") }
                )
            }
        }
    } else
        LoadingScreen()
}