package com.jsmecommerce.portal3scanner.models.files

import com.jsmecommerce.portal3scanner.enums.ColorEnum
import com.jsmecommerce.portal3scanner.utils.getIntOrNull
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class Folder(
    val id: Int,
    val name: String,
    val parent_id: Int?,
    val color: ColorEnum,
    val created_at: String,
    val updated_at: String
) {
    companion object {
        fun fromJSON(obj: JSONObject): Folder = Folder(
            obj.getInt("id"),
            obj.getString("name"),
            obj.getIntOrNull("parent_id"),
            ColorEnum.fromString(obj.getString("color")),
            obj.getString("created_at"),
            obj.getString("updated_at")
        )
        fun fromJSONArray(obj: JSONArray): List<Folder> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
