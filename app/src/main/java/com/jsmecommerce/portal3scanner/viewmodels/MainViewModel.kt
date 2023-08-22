package com.jsmecommerce.portal3scanner.viewmodels

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.models.BatteryInfo
import com.jsmecommerce.portal3scanner.models.Popup
import com.jsmecommerce.portal3scanner.ui.components.popups.DrawerPopup

class MainViewModel : ViewModel() {
    private var _title = MutableLiveData("")
    private var _backEnabled = MutableLiveData(false)
    private var _loading = MutableLiveData(false)
    private var _popup = MutableLiveData<Popup?>(null)
    private var _actions = MutableLiveData<(@Composable () -> Unit)?>(null)
    var _batteryCharging = MutableLiveData(false)
    var _batteryInfo = MutableLiveData<BatteryInfo?>(null)
    var _time = MutableLiveData<String>("")
    val title: LiveData<String> get() = _title
    val backEnabled: LiveData<Boolean> get() = _backEnabled
    val loading: LiveData<Boolean> get() = _loading
    val popup: LiveData<Popup?> get() = _popup
    val batteryCharging: LiveData<Boolean> get() = _batteryCharging
    val batteryInfo: LiveData<BatteryInfo?> get() = _batteryInfo
    val time: LiveData<String> get() = _time
    val actions: LiveData<(@Composable () -> Unit)?> get() = _actions

    fun init(title: String, disableBack: Boolean = false) {
        _title.value = title
        _backEnabled.value = !disableBack
        _loading.value = false
        _popup.value = null
        _actions.value = null
    }
    fun setTitle(title: String) {
        _title.value = title
    }

    fun setLoading(loading: Boolean = true) {
        _loading.value = loading
    }

    fun setPopup(popup: (@Composable () -> Unit)? = null) {
        if(popup == null)
            _popup.value = null
        else
            _popup.value = Popup(
                content = popup
            )
    }

    fun setPopup(backdrop: Boolean = true, popup: (@Composable () -> Unit)? = null) {
        if(popup == null)
            _popup.value = null
        else
            _popup.value = Popup(
                content = popup,
                backdrop = backdrop
            )
    }

    fun setPopup(backdrop: Boolean = true, onClose: (() -> Unit)? = null, popup: (@Composable () -> Unit)? = null) {
        if(popup == null)
            _popup.value = null
        else
            _popup.value = Popup(
                content = popup,
                backdrop = backdrop,
                onClose = onClose
            )
    }

    fun setDrawer(@StringRes title: Int, onClose: (() -> Unit)? = null, content: @Composable () -> Unit) {
        _popup.value = Popup(
            raw = true,
            content = {
                DrawerPopup(title = stringResource(id = title), onClose = {
                    setPopup(null)
                    onClose?.let { it() }
                }) {
                    content()
                }
            }
        )
    }

    fun setActions(actions: (@Composable () -> Unit)? = null) {
        _actions.value = actions
    }
}