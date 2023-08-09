package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.JSON
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
        fun fromJSON(obj: JSONObject): Address {
            val item = JSON(obj)
            return Address(
                item.stringOrNull("full_name"),
                item.stringOrNull("email"),
                item.stringOrNull("phone"),
                item.stringOrNull("road"),
                item.stringOrNull("postcode"),
                item.stringOrNull("house_number"),
                item.stringOrNull("city"),
                item.stringOrNull("state"),
                item.stringOrNull("country")
            )
        }
    }
}