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
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.utils.Api
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun OrdersOverview(nav: NavHostController, mvm: MainViewModel) {
    var orders by remember { mutableStateOf<List<OverviewOrder>?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mvm.init(
            title = context.getString(R.string.orders_title),
            disableBack = true
        )
        mvm.setLoading()
        GlobalScope.launch(Dispatchers.IO) {
            val res = Api.Request(context, "/orders")
                .exec()
            if(!res.hasError) {
                val JSONOrders = res.getJsonObject()?.getJSONArray("items")
                if(JSONOrders != null)
                    orders = OverviewOrder.fromJSONArray(JSONOrders)

            }
            withContext(Dispatchers.Main) {
                mvm.setLoading(true)
            }
        }
    }

    ScannerHost(nav = nav)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 8.dp),

        ) {
        orders?.forEach { order ->
            Spacer(modifier = Modifier.height(8.dp))
            OverviewOrder(
                order = order,
                onClick = { nav.navigate("orders/view/${order.id}?title=${order.ordernumber_full}") }
            )
        }
    }
}