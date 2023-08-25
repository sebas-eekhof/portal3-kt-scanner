package com.jsmecommerce.portal3scanner.datasource.portal3api.models.products

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.categories.OverviewCategory
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.PriceMarginTypeEnum
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.VatEnum
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Tag
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.suppliers.DefaultSupplierProduct

data class OrderRuleProduct(
    val id: Int,
    val name: String,
    val price: Double,
    val vat: VatEnum,
    val ean: String?,
    val tags: List<Tag>,
    val category: OverviewCategory,
    @SerializedName("price_margin_type")
    val priceMarginType: PriceMarginTypeEnum,
    @SerializedName("price_margin_amount")
    val priceMarginAmount: Double,
    @SerializedName("default_supplier_product")
    val defaultSupplierProduct: DefaultSupplierProduct?
)
