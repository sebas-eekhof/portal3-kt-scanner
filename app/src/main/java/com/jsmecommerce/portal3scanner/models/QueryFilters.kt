package com.jsmecommerce.portal3scanner.models

import androidx.lifecycle.MutableLiveData

class QueryFilters() {
    private val map = MutableLiveData(HashMap<String, String>())
    val value get() = map
    private fun perform(callback: (map: HashMap<String, String>) -> Unit) {
        val m2 = map.value!!
        callback(m2)
        map.value = m2
    }
    fun clear() = perform { it.clear() }
    fun remove(key: String) = perform { it.remove(key) }
    fun put(key: String, value: String?) {
        if(value == null)
            remove(key)
        else
            perform { it[key] = value }
    }
    fun put(key: String, value: Int?) = put(key, value?.toString())
    fun put(key: String, value: Boolean?) = put(key, if (value == null) null else (if (value) "TRUE" else "FALSE"))
    fun getOrNull(key: String): String? = map.value!![key]
}