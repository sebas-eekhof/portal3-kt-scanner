package com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.VatEnum
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.products.OrderRuleProduct

data class OrderRule(
    val id: Int,
    val dropshipped: Boolean,
    @SerializedName("parent_id")
    val parentId: Int?,
    val title: String,
    val description: String?,
    val quantity: Int,
    val price: Double,
    val discount: Double,
    val vat: VatEnum,
    @SerializedName("is_group_product")
    val isGroupProduct: Boolean,
    val product: OrderRuleProduct?,
    @SerializedName("scans_amount")
    val scansAmount: Int,
    val stock: Int
) {
    val totalEx = quantity.toDouble() * (price - discount)
}
