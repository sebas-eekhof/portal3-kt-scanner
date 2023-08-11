package com.jsmecommerce.portal3scanner.workers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.jsmecommerce.portal3scanner.utils.Api
import com.jsmecommerce.portal3scanner.utils.Permissions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject
import java.util.concurrent.Future
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProbeWorker(private val context: Context, private val params: WorkerParameters): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Looper.prepare()
        if(
            !Permissions.has(Manifest.permission.ACCESS_FINE_LOCATION, applicationContext) ||
            !Permissions.has(Manifest.permission.ACCESS_COARSE_LOCATION, applicationContext)
        ) return Result.failure()
        val loc = getLocation()
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { context.registerReceiver(null, it) }
        val batteryPercentage: Float? = batteryStatus?.let { it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / it.getIntExtra(BatteryManager.EXTRA_SCALE, -1).toFloat() }
        val res = Api.Request(context, "/scanner/probe")
            .setMethod(Api.RequestMethod.POST)
            .setBody(
                JSONObject()
                    .put("battery", batteryPercentage ?: 0f)
                    .put("location", JSONObject()
                        .put("lat", loc?.latitude ?: 0)
                        .put("lon", loc?.longitude ?: 0)
                        .put("alt", loc?.altitude ?: 0)
                        .put("acc", loc?.accuracy ?: 0))
            )
            .exec()
        if(res.hasError) {
            println("Probe error: ${res.error?.message}")
            return Result.failure()
        }
        return Result.success()

    }

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location? = suspendCoroutine { cont ->
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { cont.resume(it) }
            .addOnFailureListener { cont.resume(null) }
    }
}