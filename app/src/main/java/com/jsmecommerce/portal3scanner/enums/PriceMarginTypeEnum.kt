package com.jsmecommerce.portal3scanner.enums

enum class PriceMarginTypeEnum {
    PERCENTAGE,
    FIXED;

    companion object {
        fun fromString(string: String): PriceMarginTypeEnum = when(string) {
            "percentage" -> PERCENTAGE
            "fixed" -> FIXED
            else -> FIXED
        }
    }
}