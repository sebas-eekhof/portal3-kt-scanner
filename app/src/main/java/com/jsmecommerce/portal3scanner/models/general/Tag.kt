package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.enums.ColorEnum
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class Tag(
    val id: Int,
    val title: String,
    val color: ColorEnum
) {
    companion object {
        fun fromJSON(obj: JSONObject): Tag {
            return Tag(
                obj.getInt("id"),
                obj.getString("title"),
                ColorEnum.fromString(obj.getString("color"))
            )
        }

        fun fromJSONArray(obj: JSONArray): List<Tag> {
            return obj.toJSONObjectList().map { fromJSON(it) }
        }
    }
}