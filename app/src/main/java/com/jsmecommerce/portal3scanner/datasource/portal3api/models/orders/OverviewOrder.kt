package com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.customers.Customer
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Tag
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.stores.Store

data class OverviewOrder(
    val id: Int,
    @SerializedName("ordernumber")
    val orderNumber: Int,
    @SerializedName("ordernumber_full")
    val orderNumberFull: String,
    @SerializedName("is_concept")
    val isConcept: Boolean,
    @SerializedName("is_paid")
    val isPaid: Boolean,
    val store: Store,
    val status: OrderStatus,
    val rules: OverviewOrderRules,
    val tags: List<Tag>,
    val customer: Customer,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val pointer: String,
    @SerializedName("payment_method")
    val paymentMethod: String? = null
) {
    data class OverviewOrderRules(
        val total: Int,
        @SerializedName("in_stock")
        val inStock: Boolean
    )
}
