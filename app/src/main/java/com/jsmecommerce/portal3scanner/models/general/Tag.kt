package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.enums.ColorEnum

data class Tag(
    val id: Int,
    val title: String,
    val color: ColorEnum
)