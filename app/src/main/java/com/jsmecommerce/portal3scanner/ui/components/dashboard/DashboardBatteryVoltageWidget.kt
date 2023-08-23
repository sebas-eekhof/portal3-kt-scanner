package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel

@Composable
fun DashboardBatteryVoltageWidget(coreViewModel: CoreViewModel) {
    val batteryInfo: BatteryInfo? by coreViewModel.batteryInfo.observeAsState(null)

    DashboardWidget(
        icon = R.drawable.ic_bolt,
        title = R.string.dashboard_battery_voltage,
        value = "${((batteryInfo?.voltage ?: 0) / 1000f)} V"
    )
}