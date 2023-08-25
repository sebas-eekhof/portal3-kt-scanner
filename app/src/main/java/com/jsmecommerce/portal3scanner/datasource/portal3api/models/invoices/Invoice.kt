package com.jsmecommerce.portal3scanner.datasource.portal3api.models.invoices

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.files.GenericFile

data class Invoice(
    val id: Int,
    @SerializedName("is_concept")
    val isConcept: Boolean,
    val type: InvoiceType,
    val description: String?,
    @SerializedName("invoicenumber")
    val invoiceNumber: Int?,
    @SerializedName("invoicenumber_full")
    val invoiceNumberFull: String?,
    val text: String?,
    val files: List<GenericFile>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) {
    enum class InvoiceType {
        CREDIT,
        DEBIT,
        QUOTA
    }
}
