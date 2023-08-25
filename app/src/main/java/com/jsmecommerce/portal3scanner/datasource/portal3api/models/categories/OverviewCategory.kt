package com.jsmecommerce.portal3scanner.datasource.portal3api.models.categories

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.ColorEnum

data class OverviewCategory(
    val id: Int,
    val name: String,
    val color: ColorEnum
)
