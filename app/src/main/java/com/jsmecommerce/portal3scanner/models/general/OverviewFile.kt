package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.getStringOrNull
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class OverviewFile(
    val id: Int,
    val content_type: String,
    val name: String,
    val uuid: String,
    val extension: String?
) {
    companion object {
        fun fromJSON(obj: JSONObject): OverviewFile = OverviewFile(
            obj.getInt("id"),
            obj.getString("content_type"),
            obj.getString("name"),
            obj.getString("uuid"),
            obj.getStringOrNull("extension")
        )

        fun fromJSONArray(obj: JSONArray): List<OverviewFile> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
