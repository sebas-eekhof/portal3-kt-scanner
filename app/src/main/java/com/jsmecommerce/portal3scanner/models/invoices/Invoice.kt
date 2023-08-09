package com.jsmecommerce.portal3scanner.models.invoices

import com.jsmecommerce.portal3scanner.models.general.File
import com.jsmecommerce.portal3scanner.utils.JSON
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
    val files: List<File>,
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
        fun fromJSON(obj: JSONObject): Invoice {
            val item = JSON(obj)
            return Invoice(
                obj.getInt("id"),
                obj.getBoolean("is_concept"),
                InvoiceType.fromString(obj.getString("type")),
                item.stringOrNull("description"),
                item.intOrNull("invoicenumber"),
                item.stringOrNull("invoicenumber_full"),
                item.stringOrNull("text"),
                File.fromJSONArray(obj.getJSONArray("files")),
                obj.getString("created_at"),
                obj.getString("updated_at")
            )
        }
        fun fromJSONArray(obj: JSONArray): List<Invoice> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
