package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel

@Composable
fun DashboardBatteryCapacityWidget(coreViewModel: CoreViewModel) {
    val batteryInfo: BatteryInfo? by coreViewModel.batteryInfo.observeAsState(null)

    DashboardWidget(
        icon = R.drawable.ic_battery_4,
        title = R.string.dashboard_battery_capacity,
        value = "${batteryInfo?.ratedCapacity ?: 0} mAh"
    )
}