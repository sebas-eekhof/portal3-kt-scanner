package com.jsmecommerce.portal3scanner.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.models.BatteryInfo

class MainViewModel : ViewModel() {
    private var _title = MutableLiveData("")
    private var _backEnabled = MutableLiveData(false)
    private var _loading = MutableLiveData(false)
    private var _popup = MutableLiveData<(@Composable () -> Unit)?>(null)
    var _batteryCharging = MutableLiveData(false)
    var _batteryInfo = MutableLiveData<BatteryInfo?>(null)
    var _time = MutableLiveData<String>("")
    val title: LiveData<String> get() = _title
    val backEnabled: LiveData<Boolean> get() = _backEnabled
    val loading: LiveData<Boolean> get() = _loading
    val popup: LiveData<(@Composable () -> Unit)?> get() = _popup
    val batteryCharging: LiveData<Boolean> get() = _batteryCharging
    val batteryInfo: LiveData<BatteryInfo?> get() = _batteryInfo
    val time: LiveData<String> get() = _time

    fun init(title: String, disableBack: Boolean = false) {
        _title.value = title
        _backEnabled.value = !disableBack
        _loading.value = false
        _popup.value = null
    }
    fun setTitle(title: String) {
        _title.value = title
    }

    fun setLoading(loading: Boolean = true) {
        _loading.value = loading
    }

    fun setPopup(popup: (@Composable () -> Unit)? = null) {
        _popup.value = popup
    }
}