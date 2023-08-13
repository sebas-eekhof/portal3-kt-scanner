package com.jsmecommerce.portal3scanner.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@Throws(JSONException::class)
fun JSONArray.toJSONObjectList(): List<JSONObject> {
    val list = mutableListOf<JSONObject>()
    for (i in 0 until this.length())
        list.add(this.getJSONObject(i))
    return list
}

fun JSONObject.getStringOrNull(key: String): String? {
    if(!has(key)) return null
    try {
        val res = getString(key)
        if(res == "null") return null
        return res
    } catch(e: JSONException) { return null }
}

fun JSONObject.getIntOrNull(key: String): Int? {
    if(!has(key)) return null
    return try { getInt(key) } catch(e: JSONException) { null }
}

fun JSONObject.getBooleanOrNull(key: String): Boolean? {
    if(!has(key)) return null
    return try { getBoolean(key) } catch(e: JSONException) { null }
}

fun JSONObject.getJSONObjectOrNull(key: String): JSONObject? {
    if(!has(key)) return null
    return try { getJSONObject(key) } catch(e: JSONException) { null }
}

fun JSONObject.getJSONArrayOrNull(key: String): JSONArray? {
    if(!has(key)) return null
    return try { getJSONArray(key) } catch(e: JSONException) { null }
}