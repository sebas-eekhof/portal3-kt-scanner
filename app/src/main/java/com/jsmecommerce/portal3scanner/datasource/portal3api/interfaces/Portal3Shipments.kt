package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.shipments.ShipmentLabelLookup
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Portal3Shipments {
    @GET("/shipments/by_barcode")
    suspend fun byBarcode(@Query("barcode") barcode: String): Response<ShipmentLabelLookup>
}