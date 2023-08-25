package com.jsmecommerce.portal3scanner.datasource.portal3api.models.shipments

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.ShipmentLabelLookupOrder

data class ShipmentLabelLookup(
    val id: Int,
    val order: ShipmentLabelLookupOrder,
    val barcode: String
)