package com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums

enum class VatEnum {
    NONE,
    LOW,
    HIGH;

    companion object {
        fun fromString(string: String): VatEnum = when(string) {
            "none" -> NONE
            "low" -> LOW
            else -> HIGH
        }
    }
}