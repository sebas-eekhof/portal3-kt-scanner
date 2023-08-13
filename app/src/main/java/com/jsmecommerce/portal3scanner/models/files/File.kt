package com.jsmecommerce.portal3scanner.models.files

import com.jsmecommerce.portal3scanner.utils.getIntOrNull
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class File(
    val id: Int,
    val content_type: String,
    val name: String,
    val uuid: String,
    val extension: String,
    val folder_id: Int?,
    val created_at: String,
    val updated_at: String
) {
    companion object {
        fun fromJSON(obj: JSONObject): File = File(
            obj.getInt("id"),
            obj.getString("content_type"),
            obj.getString("name"),
            obj.getString("uuid"),
            obj.getString("extension"),
            obj.getIntOrNull("folder_id"),
            obj.getString("created_at"),
            obj.getString("updated_at")
        )
        fun fromJSONArray(obj: JSONArray): List<File> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
