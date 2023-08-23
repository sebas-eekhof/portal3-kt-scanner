package com.jsmecommerce.portal3scanner.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.app.Constants
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.models.Scan
import com.jsmecommerce.portal3scanner.receivers.BatteryStatusReceiver
import com.jsmecommerce.portal3scanner.receivers.ScanReceiver
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Device
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.reflect.KProperty

class CoreViewModel(private val application: Application) : ViewModel() {
    private var scanReceiver: ScanReceiver = ScanReceiver { scans.emit(it) }
    private var batteryStatusReceiver: BatteryStatusReceiver = BatteryStatusReceiver(
        callbackCharging = { _batteryCharging.value = it },
        callbackInfo = { _batteryInfo.value = it }
    )
    val device = Device(application.applicationContext)
    val auth = Auth(application.applicationContext)
    val scans = MutableSharedFlow<Scan>()

    private var _batteryCharging = MutableLiveData(false)
    private var _batteryInfo = MutableLiveData<BatteryInfo?>(null)

    val batteryCharging: LiveData<Boolean> get() = _batteryCharging
    val batteryInfo: LiveData<BatteryInfo?> get() = _batteryInfo

    init {
        with(application) {
            registerReceiver(scanReceiver, IntentFilter(Constants.INTENT_SCAN_RECEIVE))
            registerReceiver(
                batteryStatusReceiver,
                IntentFilter().apply {
                    addAction(Intent.ACTION_BATTERY_CHANGED)
                    addAction(Intent.ACTION_POWER_CONNECTED)
                    addAction(Intent.ACTION_POWER_DISCONNECTED)
                }
            )
        }
    }

    override fun onCleared() {
        with(application) {
            unregisterReceiver(scanReceiver)
            unregisterReceiver(batteryStatusReceiver)
        }
    }

    class Lazy {
        companion object {
            var inited: CoreViewModel? = null
        }

        operator fun getValue(thisRef: Activity, property: KProperty<*>): CoreViewModel {
            if(inited != null)
                return inited!!
            inited = CoreViewModel(thisRef.application)
            return inited!!
        }

    }
}