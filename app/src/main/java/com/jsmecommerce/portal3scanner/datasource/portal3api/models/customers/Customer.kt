package com.jsmecommerce.portal3scanner.datasource.portal3api.models.customers

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Address

data class Customer(
    val id: Int,
    @SerializedName("customer_type")
    val customerType: CustomerType? = null,
    @SerializedName("company_name")
    val companyName: String? = null,
    @SerializedName("company_coc")
    val companyCoc: String? = null,
    @SerializedName("company_vat")
    val companyVat: String? = null,
    @SerializedName("admin_address")
    val adminAddress: Address? = null,
    @SerializedName("delivery_address")
    val deliveryAddress: Address? = null
) {
    enum class CustomerType {
        company,
        private
    }
}