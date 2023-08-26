package com.jsmecommerce.portal3scanner.utils

import androidx.compose.runtime.snapshots.SnapshotStateMap

fun SnapshotStateMap<String, String>.put(key: String, value: String?) {
    if(value == null)
        remove(key)
    else
        put(key, value)
}

fun SnapshotStateMap<String, String>.put(key: String, value: Int?) {
    if(value == null)
        remove(key)
    else
        put(key, value.toString())
}

fun SnapshotStateMap<String, String>.put(key: String, value: Boolean?) {
    if(value == null)
        remove(key)
    else
        put(key, if (value) "TRUE" else "FALSE")
}