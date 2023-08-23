package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel

@Composable
fun DashboardBatteryTemperatureWidget(coreViewModel: CoreViewModel) {
    val batteryInfo: BatteryInfo? by coreViewModel.batteryInfo.observeAsState(null)

    DashboardWidget(
        icon = R.drawable.ic_celcius,
        title = R.string.dashboard_battery_temperature,
        value = "${((batteryInfo?.temperature ?: 0) / 10f)} °C"
    )
}