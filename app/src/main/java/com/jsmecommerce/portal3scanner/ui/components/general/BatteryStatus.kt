package com.jsmecommerce.portal3scanner.ui.components.general

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

private class BatteryStatusReceiver(val mvm: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null) return
        when(intent.action) {
            Intent.ACTION_POWER_CONNECTED -> mvm._batteryCharging.value = true
            Intent.ACTION_POWER_DISCONNECTED -> mvm._batteryCharging.value = false
            Intent.ACTION_BATTERY_CHANGED -> {
                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                mvm._batteryInfo.value = BatteryInfo(
                    level,
                    scale
                )
            }
        }
    }
}

@Composable
fun BatteryStatus(mvm: MainViewModel) {
    val context = LocalContext.current
    val isCharging: Boolean by mvm.batteryCharging.observeAsState(false)
    val batteryInfo: BatteryInfo? by mvm.batteryInfo.observeAsState(null)

    DisposableEffect(LocalLifecycleOwner.current) {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { context.registerReceiver(null, it) }
        val batteryInfo: BatteryInfo? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            BatteryInfo(
                level = level,
                scale = scale
            )
        }
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        if(batteryInfo != null) {
            mvm._batteryCharging.value = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
            mvm._batteryInfo.value = batteryInfo
        }
        val receiver = BatteryStatusReceiver(mvm = mvm)
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        context.registerReceiver(receiver, intentFilter)
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = if (isCharging) R.drawable.ic_battery_charging else (if (batteryInfo == null) R.drawable.ic_battery_0 else batteryInfo!!.icon)), contentDescription = "Battery", tint = Color.White)
        Spacer(modifier = Modifier.width(4.dp))
        SimpleText("${batteryInfo?.percentage?.toInt() ?: 0} %")
    }
}