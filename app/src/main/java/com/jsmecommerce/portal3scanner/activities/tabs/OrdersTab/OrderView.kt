package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs.OrderViewInfo
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs.OrderViewScan
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderViewTabs.OrderViewShipments
import com.jsmecommerce.portal3scanner.models.TabbarTab
import com.jsmecommerce.portal3scanner.ui.components.general.Tabbar
import com.jsmecommerce.portal3scanner.ui.components.screens.LoadingScreen
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.OrderViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel

@Composable
fun OrderView(
    nav: NavHostController,
    coreViewModel: CoreViewModel,
    uiViewModel: UiViewModel,
    orderId: Int,
    title: String,
    vm: OrderViewModel = OrderViewModel(LocalContext.current, orderId)
) {
    val order by vm.order.observeAsState(null)

    LaunchedEffect(Unit) {
        uiViewModel.init(
            title = title
        )
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