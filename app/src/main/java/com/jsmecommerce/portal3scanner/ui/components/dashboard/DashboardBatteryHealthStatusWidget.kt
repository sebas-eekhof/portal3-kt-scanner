package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel

@Composable
fun DashboardBatteryHealthStatusWidget(coreViewModel: CoreViewModel) {
    val batteryInfo: BatteryInfo? by coreViewModel.batteryInfo.observeAsState(null)

    DashboardWidget(
        icon = R.drawable.ic_activity,
        title = R.string.dashboard_battery_health,
        value = stringResource(id = if (batteryInfo?.isHealthy == true) R.string.dashboard_battery_health_healthy else R.string.dashboard_battery_health_unhealthy)
    )
}