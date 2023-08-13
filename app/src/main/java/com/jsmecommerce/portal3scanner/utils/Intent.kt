package com.jsmecommerce.portal3scanner.utils

import android.content.Intent

fun Intent.getDebugExtra(key: String, disableType: Boolean = false): String {
    if(extras == null)
        return "NO BUNDLE"
    val item = extras!!.get(key) ?: return "NULL"
    fun withType(type: String, res: String): String = "${if (disableType) "" else "($type) "}$res"
    return when(item) {
        is String -> withType("String", item)
        is Int -> withType("Int", item.toString())
        is Boolean -> withType("Boolean", if (item) "TRUE" else "FALSE")
        is Long -> withType("Long", item.toString())
        is Double -> withType("Double", item.toString())
        is Float -> withType("Float", item.toString())
        is IntArray -> withType("IntArray", item.joinToString(", "))
        else -> "UNKNOWN"
    }
}

fun Intent.getIntOrNull(key: String): Int? {
    if(extras == null) return null
    val item = extras!!.get(key) ?: return null
    return if (item is Int) item else null
}

fun Intent.getStringOrNull(key: String): String? {
    if(extras == null) return null
    val item = extras!!.get(key) ?: return null
    return if (item is String) item else null
}