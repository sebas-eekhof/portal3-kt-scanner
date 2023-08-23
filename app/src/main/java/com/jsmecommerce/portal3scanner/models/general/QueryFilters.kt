package com.jsmecommerce.portal3scanner.models.general

class QueryFilters(private var map: HashMap<String, String> = HashMap()) {
    fun clear() = map.clear()
    fun remove(key: String) = map.remove(key)
    fun put(key: String, value: String?) {
        if(value == null)
            remove(key)
        else
            map[key] = value
    }
    fun put(key: String, value: Int?) = put(key, value?.toString())
    fun put(key: String, value: Boolean?) = put(key, if (value == null) null else (if (value) "TRUE" else "FALSE"))
}