package com.jsmecommerce.portal3scanner.utils

import java.io.File
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
}