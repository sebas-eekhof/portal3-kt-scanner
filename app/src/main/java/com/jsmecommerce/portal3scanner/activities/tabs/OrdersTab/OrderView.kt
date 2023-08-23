package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import android.widget.Toast
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
import com.jsmecommerce.portal3scanner.models.Scan
import com.jsmecommerce.portal3scanner.models.TabbarTab
import com.jsmecommerce.portal3scanner.models.orders.Order
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.general.Tabbar
import com.jsmecommerce.portal3scanner.ui.components.popups.LoadingPopup
import com.jsmecommerce.portal3scanner.ui.components.screens.LoadingScreen
import com.jsmecommerce.portal3scanner.utils.Api
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun OrderView(nav: NavHostController, coreViewModel: CoreViewModel, uiViewModel: UiViewModel, orderId: Int, title: String) {
    var order by remember { mutableStateOf<Order?>(null) }
    val context = LocalContext.current

    fun fetchOrder() {
        CoroutineScope(Dispatchers.IO).launch {
            val res = Api.Request(context, "/orders/get")
                .setQuery("order_id", orderId.toString())
                .exec()
            if(!res.hasError) {
                val jsonRes = res.getJsonObject()
                if(jsonRes != null) {
                    order = Order.fromJSON(jsonRes)
                    withContext(Dispatchers.Main) {
                        uiViewModel.setTitle(order!!.ordernumber_full ?: title)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        uiViewModel.init(
            title = title
        )
        fetchOrder()
    }

    ScannerHost(nav = nav) {
        if(it.barcodeType != null && it.barcodeSubType != null && order != null) {
            if(
                listOf(
                    Scan.BarcodeType.PRODUCT,
                    Scan.BarcodeType.EAN
                ).contains(it.barcodeType) &&
                listOf(
                    Scan.BarcodeSubType.EAN,
                    Scan.BarcodeSubType.UPS,
                    Scan.BarcodeSubType.PRODUCT_ID
                ).contains(it.barcodeSubType)
            ) {
                val rules =
                    if(listOf(Scan.BarcodeSubType.EAN, Scan.BarcodeSubType.UPS).contains(it.barcodeSubType))
                        order!!.rules.filter { rule ->
                            if(rule.product?.ean == null || rule.scans_amount >= rule.quantity)
                                false
                            else
                                rule.product.ean == it.barcode
                        }
                    else
                        order!!.rules.filter { rule ->
                            if(rule.product?.ean == null || rule.scans_amount >= rule.quantity)
                                false
                            else
                                rule.product.id == it.barcode.toInt()
                        }
                if(rules.isEmpty())
                    Toast.makeText(context, "Geen geldige productregel gevonden voor deze scan", Toast.LENGTH_LONG).show();
                else {
                    uiViewModel.setPopup(false) {
                        LoadingPopup(text = R.string.orders_processing_scan)
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        val res = Api.Request(context, "/orders/rules/scan")
                            .setMethod(Api.RequestMethod.POST)
                            .setQuery("order_id", order!!.id.toString())
                            .setBody(
                                JSONObject()
                                    .put(
                                        "items",
                                        JSONArray()
                                            .put(
                                                JSONObject()
                                                    .put("order_rule_id", rules.first().id)
                                                    .put("amount", 1)
                                            )
                                    )
                            )
                            .exec()
                        if(!res.hasError)
                            fetchOrder()
                        uiViewModel.setPopup(null)
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