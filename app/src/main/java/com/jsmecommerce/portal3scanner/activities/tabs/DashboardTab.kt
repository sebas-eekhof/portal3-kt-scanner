package com.jsmecommerce.portal3scanner.activities.tabs

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
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun DashboardTab(nav: NavHostController, mvm: MainViewModel) {
    val context = LocalContext.current
    val auth = Auth(context)
    val user = auth.getUser()!!

    LaunchedEffect(Unit) {
        mvm.init(
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
            Column(Modifier.weight(1f)) { DashboardBatteryChargeWidget(mvm = mvm) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryHealthStatusWidget(mvm = mvm) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.weight(1f)) { DashboardBatteryCapacityWidget(mvm = mvm) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryTemperatureWidget(mvm = mvm) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.weight(1f)) { DashboardBatteryVoltageWidget(mvm = mvm) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryCurrentWidget(mvm = mvm) }
        }
    }
}