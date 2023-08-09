package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.models.general.App
import com.jsmecommerce.portal3scanner.utils.JSON
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class OrderShipment(
    val id: Int,
    val barcode: String?,
    val description: String?,
    val sub_icon: String?,
    val app: App?,
    val file: ShipmentFile?,
    val created_at: String,
    val updated_at: String
) {
    data class ShipmentFile(
        val id: Int,
        val name: String,
        val uuid: String
    ) {
        companion object {
            fun fromJSON(obj: JSONObject): ShipmentFile = ShipmentFile(
                obj.getInt("id"),
                obj.getString("name"),
                obj.getString("uuid")
            )
        }
    }

    companion object {
        fun fromJSON(obj: JSONObject): OrderShipment {
            val item = JSON(obj)
            return OrderShipment(
                obj.getInt("id"),
                item.stringOrNull("barcode"),
                item.stringOrNull("description"),
                item.stringOrNull("sub_icon"),
                if (obj.isNull("app")) null else App.fromJSON(obj.getJSONObject("app")),
                if (obj.isNull("file")) null else ShipmentFile.fromJSON(obj.getJSONObject("file")),
                obj.getString("created_at"),
                obj.getString("updated_at")
            )
        }
        fun fromJSONArray(obj: JSONArray): List<OrderShipment> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
