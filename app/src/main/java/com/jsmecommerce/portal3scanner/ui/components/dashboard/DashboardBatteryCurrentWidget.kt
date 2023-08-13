package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun DashboardBatteryCurrentWidget(mvm: MainViewModel) {
    val batteryInfo: BatteryInfo? by mvm.batteryInfo.observeAsState(null)
    val isCharging: Boolean by mvm.batteryCharging.observeAsState(false)

    val value = "${(batteryInfo?.current ?: 0) / 1000} mAh"

    DashboardWidget(
        icon = R.drawable.ic_recharging,
        title = R.string.dashboard_battery_current
    ) {
        if(isCharging)
            SimpleText(value, color = if ((batteryInfo?.current ?: 0) < 0) Color.Danger.Regular else (if ((batteryInfo?.current ?: 0) < 1000000) Color.Warning.Regular else Color.Success.Regular))
        else
            Description(value)
    }
}