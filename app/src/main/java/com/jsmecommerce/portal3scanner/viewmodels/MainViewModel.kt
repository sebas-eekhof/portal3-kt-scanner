package com.jsmecommerce.portal3scanner.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _title = MutableLiveData("")
    private var _backEnabled = MutableLiveData(false)
    private var _loading = MutableLiveData(false)
    val title: LiveData<String> get() = _title
    val backEnabled: LiveData<Boolean> get() = _backEnabled
    val loading: LiveData<Boolean> get() = _loading

    fun init(title: String, disableBack: Boolean = false) {
        _title.value = title
        _backEnabled.value = !disableBack
        _loading.value = false
    }
    fun setTitle(title: String) {
        _title.value = title
    }

    fun setLoading(loading: Boolean = true) {
        _loading.value = loading
    }
}