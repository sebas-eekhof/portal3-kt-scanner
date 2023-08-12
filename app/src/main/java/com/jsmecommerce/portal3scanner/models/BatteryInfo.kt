package com.jsmecommerce.portal3scanner.models

import com.jsmecommerce.portal3scanner.R

data class BatteryInfo(
    val level: Int,
    val scale: Int
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
}
