package com.jsmecommerce.portal3scanner.models.general

data class Address(
    val full_name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val road: String? = null,
    val postcode: String? = null,
    val house_number: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null
)