package com.jsmecommerce.portal3scanner.datasource.portal3api.models.general

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("full_name")
    val fullName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val road: String? = null,
    val postcode: String? = null,
    @SerializedName("house_number")
    val houseNumber: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null
)