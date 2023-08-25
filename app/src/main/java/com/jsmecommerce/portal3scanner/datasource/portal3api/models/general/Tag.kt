package com.jsmecommerce.portal3scanner.datasource.portal3api.models.general

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.ColorEnum

data class Tag(
    val id: Int,
    val title: String,
    val color: ColorEnum
)