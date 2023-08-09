package com.jsmecommerce.portal3scanner.models.orders

import org.json.JSONObject

data class ShipmentLabelLookup(
    val id: Int,
    val order: ShipmentLabelLookupOrder,
    val barcode: String
) {
    data class ShipmentLabelLookupOrder(
        val id: Int,
        val ordernumber: Int,
        val ordernumber_full: String
    ) {
        companion object {
            fun fromJSON(obj: JSONObject): ShipmentLabelLookupOrder = ShipmentLabelLookupOrder(
                obj.getInt("id"),
                obj.getInt("ordernumber"),
                obj.getString("ordernumber_full")
            )
        }
    }

    companion object {
        fun fromJSON(obj: JSONObject): ShipmentLabelLookup = ShipmentLabelLookup(
            obj.getInt("id"),
            ShipmentLabelLookupOrder.fromJSON(obj.getJSONObject("order")),
            obj.getString("barcode")
        )
    }
}
