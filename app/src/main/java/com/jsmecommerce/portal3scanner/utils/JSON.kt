package com.jsmecommerce.portal3scanner.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JSON(val obj: JSONObject) {
    fun stringOrNull(key: String): String? = try { if(obj.has(key)) obj.getString(key) else null } catch(e: Exception) { null }
    fun intOrNull(key: String): Int? = try { if(obj.has(key)) obj.getInt(key) else null } catch(e: Exception) { null }
    fun booleanOrNull(key: String): Boolean? = try { if(obj.has(key)) obj.getBoolean(key) else null } catch(e: Exception) { null }
    fun jsonObjectOrNull(key: String): JSONObject? = try { if(obj.has(key)) obj.getJSONObject(key) else null } catch(e: Exception) { null }
    fun jsonArrayOrNull(key: String): JSONArray? = try { if(obj.has(key)) obj.getJSONArray(key) else null } catch(e: Exception) { null }
}

@Throws(JSONException::class)
fun JSONArray.toJSONObjectList(): List<JSONObject> {
    val list = mutableListOf<JSONObject>()
    for (i in 0 until this.length())
        list.add(this.getJSONObject(i))
    return list
}