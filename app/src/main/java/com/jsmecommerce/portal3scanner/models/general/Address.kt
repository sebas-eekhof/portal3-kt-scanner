package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.getStringOrNull
import org.json.JSONObject

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
) {
    companion object {
        fun fromJSON(obj: JSONObject): Address = Address(
            obj.getStringOrNull("full_name"),
            obj.getStringOrNull("email"),
            obj.getStringOrNull("phone"),
            obj.getStringOrNull("road"),
            obj.getStringOrNull("postcode"),
            obj.getStringOrNull("house_number"),
            obj.getStringOrNull("city"),
            obj.getStringOrNull("state"),
            obj.getStringOrNull("country")
        )
    }
}