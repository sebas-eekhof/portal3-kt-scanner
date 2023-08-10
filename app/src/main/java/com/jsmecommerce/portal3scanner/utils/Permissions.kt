package com.jsmecommerce.portal3scanner.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class Permissions(private val activity: ComponentActivity) {
    fun has(permission: String): Boolean = ActivityCompat.checkSelfPermission(activity.applicationContext, permission) == PackageManager.PERMISSION_GRANTED
    fun request(permission: String, onResult: (result: Boolean) -> Unit) {
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { onResult(it) }.launch(permission)
    }
    fun request(permission: List<String>, onResult: (result: Map<String, Boolean>) -> Unit) {
        request(permission.toTypedArray(), onResult)
    }
    fun request(permission: Array<String>, onResult: (result: Map<String, Boolean>) -> Unit) {
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { onResult(it) }.launch(permission)
    }

    companion object {
        fun has(permission: String, context: Context): Boolean = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}