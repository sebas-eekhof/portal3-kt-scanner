package com.jsmecommerce.portal3scanner.models

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.jsmecommerce.portal3scanner.R

data class BatteryInfo(
    val level: Int,
    val scale: Int,
    val ratedCapacity: Int? = null,
    val voltage: Int? = null,
    val temperature: Int? = null,
    val serialnumber: String? = null,
    val health: Int? = null,
    val maxChargingCurrent: Int? = null,
    val chargeCounter: Int? = null,
    val mfd: String? = null,
    val partnumber: String? = null,
    val batteryDecomission: Boolean? = null,
    val current: Long
) {
    val percentage: Float = (level * 100 / scale.toFloat())
    val iconLevel: Int =
        if(level <= 10) 0
        else if(level < 25) 1
        else if(level < 50) 2
        else if(level < 70) 3
        else 4
    val icon: Int = when(iconLevel) {
        4 -> R.drawable.ic_battery_4
        3 -> R.drawable.ic_battery_3
        2 -> R.drawable.ic_battery_2
        1 -> R.drawable.ic_battery_1
        else -> R.drawable.ic_battery_0
    }
    val isHealthy: Boolean get() = run {
        if(health == null && batteryDecomission == null) return true
        if(batteryDecomission != null && batteryDecomission) return false
        if(health != null && !listOf(
            BatteryManager.BATTERY_HEALTH_GOOD,
            BatteryManager.BATTERY_HEALTH_UNKNOWN
        ).contains(health)) return false
        return true
    }

    companion object {
        fun getCurrent(context: Context): BatteryInfo? {
            val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { context.registerReceiver(null, it) }
            return batteryStatus?.let { intent ->
                val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
                BatteryInfo(
                    level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1),
                    scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1),
                    current = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
                )
            }
        }

        fun isCharging(context: Context): Boolean {
            val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { context.registerReceiver(null, it) }
            val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        }
    }
}
