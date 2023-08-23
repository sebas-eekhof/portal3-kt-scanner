package com.jsmecommerce.portal3scanner.activities.tabs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.datasource.portal3api.Portal3Api
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryCapacityWidget
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryChargeWidget
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryCurrentWidget
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryHealthStatusWidget
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryTemperatureWidget
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryVoltageWidget
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.UserBanner
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Device
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DashboardTab(nav: NavHostController, coreViewModel: CoreViewModel, uiViewModel: UiViewModel) {
    val context = LocalContext.current
    val auth = Auth(context)
    val user = auth.getUser()!!

    val api = Portal3Api.getInstance(context)

    GlobalScope.launch {
        val stores = api.stores.all()
    }

    LaunchedEffect(Unit) {
        uiViewModel.init(
            title = context.getString(R.string.dashboard_title),
            disableBack = true
        )
    }

    ScannerHost(nav = nav)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        UserBanner(user = user)
        Spacer(modifier = Modifier.height(8.dp))
        SimpleText(text = "IMEI: ${Device(context).imei}")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.weight(1f)) { DashboardBatteryChargeWidget(coreViewModel = coreViewModel) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryHealthStatusWidget(coreViewModel = coreViewModel) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.weight(1f)) { DashboardBatteryCapacityWidget(coreViewModel = coreViewModel) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryTemperatureWidget(coreViewModel = coreViewModel) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.weight(1f)) { DashboardBatteryVoltageWidget(coreViewModel = coreViewModel) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryCurrentWidget(coreViewModel = coreViewModel) }
        }
    }
}