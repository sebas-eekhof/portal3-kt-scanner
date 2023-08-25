package com.jsmecommerce.portal3scanner.datasource.portal3api.models.suppliers

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.VatEnum

data class DefaultSupplierProduct(
    @SerializedName("supplier_id")
    val supplierId: Int,
    @SerializedName("article_code")
    val articleCode: String?,
    val vat: VatEnum,
    val price: Double
)
