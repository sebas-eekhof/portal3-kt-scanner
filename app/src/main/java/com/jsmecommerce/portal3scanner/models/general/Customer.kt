package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.getStringOrNull
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
        fun fromJSON(obj: JSONObject): Customer = Customer(
            obj.getInt("id"),
            when(obj.getStringOrNull("customer_type")) {
                "private" -> CustomerType.private
                "company" -> CustomerType.company
                else -> null
            },
            obj.getStringOrNull("company_name"),
            obj.getStringOrNull("company_coc"),
            obj.getStringOrNull("company_vat"),
            if (obj.isNull("admin_address")) null else Address.fromJSON(obj.getJSONObject("admin_address")),
            if (obj.isNull("delivery_address")) null else Address.fromJSON(obj.getJSONObject("delivery_address"))
        )
    }
}