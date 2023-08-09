package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.models.general.Customer
import com.jsmecommerce.portal3scanner.models.general.OverviewStore
import com.jsmecommerce.portal3scanner.models.general.Tag
import com.jsmecommerce.portal3scanner.utils.JSON
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class OverviewOrder(
    val id: Int,
    val ordernumber: Int,
    val ordernumber_full: String,
    val is_concept: Boolean,
    val is_paid: Boolean,
    val store: OverviewStore,
    val status: OrderStatus,
    val rules: OverviewOrderRules,
    val tags: List<Tag>,
    val customer: Customer,
    val created_at: String,
    val updated_at: String,
    val pointer: String,
    val payment_method: String? = null
) {
    data class OverviewOrderRules(
        val total: Int,
        val in_stock: Boolean
    ) {
        companion object {
            fun fromJSON(obj: JSONObject): OverviewOrderRules {
                return OverviewOrderRules(
                    obj.getInt("total"),
                    obj.getBoolean("in_stock")
                )
            }
        }
    }

    companion object {
        fun fromJSON(obj: JSONObject): OverviewOrder {
            val item = JSON(obj)
            return OverviewOrder(
                obj.getInt("id"),
                obj.getInt("ordernumber"),
                obj.getString("ordernumber_full"),
                obj.getBoolean("is_concept"),
                obj.getBoolean("is_paid"),
                OverviewStore.fromJSON(obj.getJSONObject("store")),
                OrderStatus.fromJSON(obj.getJSONObject("status")),
                OverviewOrderRules.fromJSON(obj.getJSONObject("rules")),
                Tag.fromJSONArray(obj.getJSONArray("tags")),
                Customer.fromJSON(obj.getJSONObject("customer")),
                obj.getString("created_at"),
                obj.getString("updated_at"),
                obj.getString("pointer"),
                item.stringOrNull("payment_method")
            )
        }

        fun fromJSONArray(obj: JSONArray): List<OverviewOrder> {
            return obj.toJSONObjectList().map { fromJSON(it) }
        }
    }
}