package com.jsmecommerce.portal3scanner.datasource.portal3api.models.products

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.categories.OverviewCategory
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Tag

data class ScanProduct(
    val id: Int,
    val name: String,
    val tags: List<Tag>,
    val category: OverviewCategory,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
