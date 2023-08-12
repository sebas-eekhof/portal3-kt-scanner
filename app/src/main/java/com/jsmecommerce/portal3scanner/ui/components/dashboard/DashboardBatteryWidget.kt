package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun DashboardBatteryWidget(mvm: MainViewModel) {
    val isCharging: Boolean by mvm.batteryCharging.observeAsState(false)
    val batteryInfo: BatteryInfo? by mvm.batteryInfo.observeAsState(null)

    DashboardWidget(
        icon = if (isCharging) R.drawable.ic_battery_charging else (if (batteryInfo == null) R.drawable.ic_battery_0 else batteryInfo!!.icon),
        title = R.string.dashboard_battery,
        value = if (isCharging && batteryInfo?.percentage?.toInt() == 100) stringResource(id = R.string.dashboard_battery_fully_charged) else "${batteryInfo?.percentage?.toInt() ?: 0} %"
    )
}