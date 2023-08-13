package com.jsmecommerce.portal3scanner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import androidx.compose.ui.text.toUpperCase
import java.io.File
import java.net.NetworkInterface
import java.text.DecimalFormat
import java.util.regex.Pattern

class Device(private val context: Context) {
    companion object {
        fun getMemoryGB(): String {
            return try {
                val meminfo = File("/proc/meminfo").readText()
                val m = Pattern.compile("(\\d+)").matcher(meminfo)
                var value: String? = null
                while(m.find() && value == null)
                    value = m.group(1)
                if(value == null)
                    return "Onbekend"
                "${DecimalFormat("#,###.00").format(value.toDouble() / 1000000)} GB"
            } catch(e: Exception) {
                e.printStackTrace()
                "Onbekend"
            }
        }
        fun getCores(): Int {
            return Runtime.getRuntime().availableProcessors()
        }
        fun getMAC(): String? {
            val iface = NetworkInterface.getByName("wlan0")
            val addr = iface.hardwareAddress
            val builder = StringBuilder("")
            if(addr == null)
                return null
            for(part in addr) {
                val v = part.toInt() and 0xFF
                val hv = Integer.toHexString(v).uppercase()
                if(hv.length < 2)
                    builder.append(0)
                builder.append(hv).append(":")
            }
            return builder.substring(0, builder.length - 1)
        }
        val isZebra get() = Build.MANUFACTURER == "Zebra Technologies"
    }

    @SuppressLint("HardwareIds")
    fun getHWID(): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }

    fun vibrateScan() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(75, VibrationEffect.EFFECT_TICK))
    }
}