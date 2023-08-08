package com.jsmecommerce.portal3scanner.models.general

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
}