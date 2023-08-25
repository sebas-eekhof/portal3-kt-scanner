package com.jsmecommerce.portal3scanner.viewmodels

import android.app.Activity
import android.os.Handler
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.models.Popup
import com.jsmecommerce.portal3scanner.ui.components.popups.DrawerPopup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.fixedRateTimer
import kotlin.reflect.KProperty

class UiViewModel : ViewModel() {
    private var _title = MutableLiveData("")
    private var _backEnabled = MutableLiveData(false)
    private var _loading = MutableLiveData(false)
    private var _popup = MutableLiveData<Popup?>(null)
    private var _actions = MutableLiveData<(@Composable () -> Unit)?>(null)
    var _date = MutableLiveData(Date())
    val title: LiveData<String> get() = _title
    val backEnabled: LiveData<Boolean> get() = _backEnabled
    val loading: LiveData<Boolean> get() = _loading
    val popup: LiveData<Popup?> get() = _popup
    val date: LiveData<Date> get() = _date
    val actions: LiveData<(@Composable () -> Unit)?> get() = _actions

    val timerHandler = Handler()
    val clockTimer = fixedRateTimer("clock", false, 0L, 60 * 1000) {
        timerHandler.post { _date.value = Date() }
    }

    override fun onCleared() {
        clockTimer.cancel()
    }

    fun init(title: String, disableBack: Boolean = false) {
        println("UI INIT")
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
        println("UI SET LOADING")
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
        println("UI SET ACTIONS")
        _actions.value = actions
    }

    class Lazy {
        companion object {
            var inited: UiViewModel? = null
        }

        operator fun getValue(thisRef: Activity, property: KProperty<*>): UiViewModel {
            if(inited != null)
                return inited!!
            inited = UiViewModel()
            return inited!!
        }

    }
}