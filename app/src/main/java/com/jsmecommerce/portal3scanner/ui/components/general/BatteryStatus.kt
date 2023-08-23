package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel

@Composable
fun BatteryStatus(coreViewModel: CoreViewModel) {
    val context = LocalContext.current
    val isCharging: Boolean by coreViewModel.batteryCharging.observeAsState(false)
    val batteryInfo: BatteryInfo? by coreViewModel.batteryInfo.observeAsState(null)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = if (isCharging) R.drawable.ic_battery_charging else (if (batteryInfo == null) R.drawable.ic_battery_0 else batteryInfo!!.icon)), contentDescription = "Battery", tint = Color.White)
        Spacer(modifier = Modifier.width(4.dp))
        SimpleText("${batteryInfo?.percentage?.toInt() ?: 0} %")
    }
}