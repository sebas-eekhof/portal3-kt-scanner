package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.enums.ColorEnum
import org.json.JSONObject

data class OverviewCategory(
    val id: Int,
    val name: String,
    val color: ColorEnum
) {
    companion object {
        fun fromJSON(obj: JSONObject): OverviewCategory = OverviewCategory(
            obj.getInt("id"),
            obj.getString("name"),
            ColorEnum.fromString(obj.getString("color"))
        )
    }
}
