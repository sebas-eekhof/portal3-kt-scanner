package com.jsmecommerce.portal3scanner.utils

import java.io.File
import java.net.NetworkInterface
import java.text.DecimalFormat
import java.util.regex.Pattern

class Device {
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

    fun getMAC(): String {
        val iface = NetworkInterface.getByName("wlan0")
        val addr = iface.hardwareAddress
        val builder = StringBuilder("")
        for(part in addr) {
            val v = part.toInt() and 0xFF
            val hv = Integer.toHexString(v).uppercase()
            if(hv.length < 2)
                builder.append(0)
            builder.append(hv).append(":")
        }
        return builder.substring(0, builder.length - 1)
    }
}