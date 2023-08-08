package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.models.general.Customer
import com.jsmecommerce.portal3scanner.models.general.OverviewStore
import com.jsmecommerce.portal3scanner.models.general.Tag

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
    )
}