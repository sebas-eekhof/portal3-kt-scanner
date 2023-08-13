package com.jsmecommerce.portal3scanner.models.invoices

import com.jsmecommerce.portal3scanner.models.general.OverviewFile
import com.jsmecommerce.portal3scanner.utils.getIntOrNull
import com.jsmecommerce.portal3scanner.utils.getStringOrNull
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class Invoice(
    val id: Int,
    val is_concept: Boolean,
    val type: InvoiceType,
    val description: String?,
    val invoicenumber: Int?,
    val invoicenumber_full: String?,
    val text: String?,
    val files: List<OverviewFile>,
    val created_at: String,
    val updated_at: String
) {
    enum class InvoiceType {
        CREDIT,
        DEBIT,
        QUOTA;
        companion object {
            fun fromString(string: String): InvoiceType = when(string) {
                "credit" -> CREDIT
                "debit" -> DEBIT
                "quota" -> QUOTA
                else -> DEBIT
            }
        }
    }

    companion object {
        fun fromJSON(obj: JSONObject): Invoice = Invoice(
            obj.getInt("id"),
            obj.getBoolean("is_concept"),
            InvoiceType.fromString(obj.getString("type")),
            obj.getStringOrNull("description"),
            obj.getIntOrNull("invoicenumber"),
            obj.getStringOrNull("invoicenumber_full"),
            obj.getStringOrNull("text"),
            OverviewFile.fromJSONArray(obj.getJSONArray("files")),
            obj.getString("created_at"),
            obj.getString("updated_at")
        )
        fun fromJSONArray(obj: JSONArray): List<Invoice> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
