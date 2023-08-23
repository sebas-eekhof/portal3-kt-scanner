package com.jsmecommerce.portal3scanner.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.utils.getIntOrNull
import com.jsmecommerce.portal3scanner.utils.getStringOrNull

class BatteryStatusReceiver(
    private val callbackInfo: (batteryInfo: BatteryInfo) -> Unit,
    private val callbackCharging: (charging: Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null || context == null) return
        when(intent.action) {
            Intent.ACTION_POWER_CONNECTED -> callbackCharging(true)
            Intent.ACTION_POWER_DISCONNECTED -> callbackCharging(false)
            Intent.ACTION_BATTERY_CHANGED -> {
                val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
                callbackInfo(
                    BatteryInfo(
                        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1),
                        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1),
                        voltage = intent.getIntOrNull("voltage"),
                        temperature = intent.getIntOrNull("temperature"),
                        chargeCounter = intent.getIntOrNull("charge_counter"),
                        health = intent.getIntOrNull("health"),
                        maxChargingCurrent = intent.getIntOrNull("max_charging_current"),
                        mfd = intent.getStringOrNull("mfd"),
                        partnumber = intent.getStringOrNull("partnumber"),
                        ratedCapacity = intent.getIntOrNull("ratedcapacity"),
                        serialnumber = intent.getStringOrNull("serialnumber"),
                        batteryDecomission = if (intent.hasExtra("battery_decommission")) (intent.getIntExtra("battery_decommission", 0) == 1) else null,
                        current = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
                    )
                )
            }
        }
    }
}