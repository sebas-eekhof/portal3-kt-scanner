package com.jsmecommerce.portal3scanner.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _title = MutableLiveData("")
    private var _backEnabled = MutableLiveData(false)
    val title: LiveData<String> get() = _title
    val backEnabled: LiveData<Boolean> get() = _backEnabled
    fun setTitle(title: String) {
        _title.value = title
    }

    fun enableBack() {
        _backEnabled.value = true
    }

    fun disableBack() {
        _backEnabled.value = false
    }
}