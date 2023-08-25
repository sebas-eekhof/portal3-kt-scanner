package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.products.ScanProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Portal3Products {
    @GET("/products/by_barcode")
    suspend fun byBarcode(@Query("barcode") barcode: String): Response<ScanProduct>
}