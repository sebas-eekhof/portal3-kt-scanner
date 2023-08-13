package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.models.general.Customer
import com.jsmecommerce.portal3scanner.models.general.Event
import com.jsmecommerce.portal3scanner.models.general.OverviewStore
import com.jsmecommerce.portal3scanner.models.general.Tag
import com.jsmecommerce.portal3scanner.models.invoices.Invoice
import com.jsmecommerce.portal3scanner.utils.getIntOrNull
import com.jsmecommerce.portal3scanner.utils.getStringOrNull
import org.json.JSONObject

data class Order(
    val id: Int,
    val ordernumber: Int?,
    val ordernumber_full: String?,
    val is_concept: Boolean,
    val is_paid: Boolean,
    val status: OrderStatus,
    val store: OverviewStore,
    val customer: Customer,
    val comments: String?,
    val tags: List<Tag>,
    val events: List<Event>,
    val invoices: List<Invoice>,
    val rules: List<OrderRule>,
    val shipments: List<OrderShipment>,
    val payment_method: String?,
    val created_at: String,
    val updated_at: String
) {
    val total_ex = rules.fold((0).toDouble()) { sum, rule -> sum + rule.total_ex}
    companion object {
        fun fromJSON(obj: JSONObject): Order = Order(
            obj.getInt("id"),
            obj.getIntOrNull("ordernumber"),
            obj.getStringOrNull("ordernumber_full"),
            obj.getBoolean("is_concept"),
            obj.getBoolean("is_paid"),
            OrderStatus.fromJSON(obj.getJSONObject("status")),
            OverviewStore.fromJSON(obj.getJSONObject("store")),
            Customer.fromJSON(obj.getJSONObject("customer")),
            obj.getStringOrNull("comments"),
            Tag.fromJSONArray(obj.getJSONArray("tags")),
            Event.fromJSONArray(obj.getJSONArray("events")),
            Invoice.fromJSONArray(obj.getJSONArray("invoices")),
            OrderRule.fromJSONArray(obj.getJSONArray("rules")),
            OrderShipment.fromJSONArray(obj.getJSONArray("shipments")),
            obj.getStringOrNull("payment_method"),
            obj.getString("created_at"),
            obj.getString("updated_at")
        )
    }
}
