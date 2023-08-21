package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.enums.ColorEnum
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

class OverviewStore(
    val id: Number,
    val name: String,
    val color: ColorEnum
) {
    companion object {
        fun fromJSON(obj: JSONObject): OverviewStore = OverviewStore(
            obj.getInt("id"),
            obj.getString("name"),
            ColorEnum.fromString(obj.getString("color"))
        )
        fun fromJSONArray(obj: JSONArray): List<OverviewStore> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}