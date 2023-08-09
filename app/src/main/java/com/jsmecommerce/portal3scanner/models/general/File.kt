package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.JSON
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class File(
    val id: Int,
    val content_type: String,
    val name: String,
    val uuid: String,
    val extension: String?
) {
    companion object {
        fun fromJSON(obj: JSONObject): File = File(
            obj.getInt("id"),
            obj.getString("content_type"),
            obj.getString("name"),
            obj.getString("uuid"),
            JSON(obj).stringOrNull("extension")
        )

        fun fromJSONArray(obj: JSONArray): List<File> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
