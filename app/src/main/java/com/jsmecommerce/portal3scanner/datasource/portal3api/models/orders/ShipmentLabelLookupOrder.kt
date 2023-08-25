package com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders

import com.google.gson.annotations.SerializedName

data class ShipmentLabelLookupOrder(
    val id: Int,
    @SerializedName("ordernumber")
    val orderNumber: Int,
    @SerializedName("ordernumber_full")
    val orderNumberFull: String
)
