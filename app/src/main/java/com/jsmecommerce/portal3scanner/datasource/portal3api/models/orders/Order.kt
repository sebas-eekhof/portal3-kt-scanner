package com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.customers.Customer
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Event
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Tag
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.invoices.Invoice
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.shipments.OrderShipment
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.stores.Store

data class Order(
    val id: Int,
    @SerializedName("ordernumber")
    val orderNumber: Int?,
    @SerializedName("ordernumber_full")
    val orderNumberFull: String?,
    @SerializedName("is_concept")
    val isConcept: Boolean,
    @SerializedName("is_paid")
    val isPaid: Boolean,
    val status: OrderStatus,
    val store: Store,
    val customer: Customer,
    val comments: String?,
    val tags: List<Tag>,
    val events: List<Event>,
    val invoices: List<Invoice>,
    val rules: List<OrderRule>,
    val shipments: List<OrderShipment>,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) {
    val totalEx = rules.fold((0).toDouble()) { sum, rule -> sum + rule.totalEx}
}
