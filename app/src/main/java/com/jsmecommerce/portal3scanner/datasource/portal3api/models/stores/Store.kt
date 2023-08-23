package com.jsmecommerce.portal3scanner.datasource.portal3api.models.stores

import com.jsmecommerce.portal3scanner.enums.ColorEnum

data class Store(
    val id: Int,
    val name: String,
    val color: ColorEnum
)