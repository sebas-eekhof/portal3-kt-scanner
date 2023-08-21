package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.enums.ColorEnum
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class OrderStatus(
    val id: Number,
    val name: String,
    val color: ColorEnum,
    val type: String
) {
    companion object {
        fun fromJSON(obj: JSONObject): OrderStatus = OrderStatus(
            obj.getInt("id"),
            obj.getString("name"),
            ColorEnum.fromString(obj.getString("color")),
            obj.getString("type")
        )
        fun fromJSONArray(obj: JSONArray): List<OrderStatus> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}