package com.jsmecommerce.portal3scanner.models

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
}
