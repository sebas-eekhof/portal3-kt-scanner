package com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums

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