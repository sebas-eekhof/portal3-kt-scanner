package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.JSON
import org.json.JSONObject

data class Customer(
    val id: Int,
    val customer_type: CustomerType? = null,
    val company_name: String? = null,
    val company_coc: String? = null,
    val company_vat: String? = null,
    val admin_address: Address? = null,
    val delivery_address: Address? = null
) {
    enum class CustomerType {
        company,
        private
    }
    companion object {
        fun fromJSON(obj: JSONObject): Customer {
            val item = JSON(obj)
            val admin_address = item.jsonObjectOrNull("admin_address")
            val delivery_address = item.jsonObjectOrNull("delivery_address")
            return Customer(
                obj.getInt("id"),
                when(item.stringOrNull("customer_type")) {
                    "private" -> CustomerType.private
                    "company" -> CustomerType.company
                    else -> null
                },
                item.stringOrNull("company_name"),
                item.stringOrNull("company_coc"),
                item.stringOrNull("company_vat"),
                if (admin_address == null) null else Address.fromJSON(admin_address),
                if (delivery_address == null) null else Address.fromJSON(delivery_address)
            )
        }
    }
}