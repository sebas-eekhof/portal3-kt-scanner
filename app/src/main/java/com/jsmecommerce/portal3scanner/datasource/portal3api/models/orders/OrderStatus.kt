package com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.ColorEnum

data class OrderStatus(
    val id: Number,
    val name: String,
    val color: ColorEnum,
    val type: String
)