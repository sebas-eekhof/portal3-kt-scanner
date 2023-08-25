package com.jsmecommerce.portal3scanner.datasource.portal3api.models.shipments

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.files.ShipmentFile
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.App

data class OrderShipment(
    val id: Int,
    val barcode: String?,
    val description: String?,
    @SerializedName("sub_icon")
    val subIcon: String?,
    val app: App?,
    val file: ShipmentFile?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
