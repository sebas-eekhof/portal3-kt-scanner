package com.jsmecommerce.portal3scanner.models

import androidx.lifecycle.MutableLiveData

class QueryFilters : HashMap<String, String>() {
    fun put(key: String, value: Int?) = value?.toString()?.let { put(key, it) }
    fun put(key: String, value: Boolean?) = value?.let { put(key, if (it) "TRUE" else "FALSE") }
}