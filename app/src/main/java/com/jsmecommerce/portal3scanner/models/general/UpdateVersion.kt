package com.jsmecommerce.portal3scanner.models.general

import org.json.JSONObject

data class UpdateVersion(
    val version_name: String,
    val version_code: Int,
    val created_at: String,
    val url: String
) {
    companion object {
        fun fromJSON(obj: JSONObject): UpdateVersion = UpdateVersion(
            obj.getString("version_name"),
            obj.getInt("version_code"),
            obj.getString("created_at"),
            obj.getString("url")
        )
    }
}
