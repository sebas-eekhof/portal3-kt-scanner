package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.enums.ColorEnum

class OrderStatus(
    val id: Number,
    val name: String,
    val color: ColorEnum,
    val type: String
) {

}