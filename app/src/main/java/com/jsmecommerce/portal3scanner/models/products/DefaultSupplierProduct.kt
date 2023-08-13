package com.jsmecommerce.portal3scanner.models.products

import com.jsmecommerce.portal3scanner.enums.VatEnum
import com.jsmecommerce.portal3scanner.utils.getStringOrNull
import org.json.JSONObject

data class DefaultSupplierProduct(
    val supplier_id: Int,
    val article_code: String?,
    val vat: VatEnum,
    val price: Double
) {
    companion object {
        fun fromJSON(obj: JSONObject): DefaultSupplierProduct = DefaultSupplierProduct(
            obj.getInt("supplier_id"),
            obj.getStringOrNull("article_code"),
            VatEnum.fromString(obj.getString("vat")),
            obj.getDouble("price")
        )
    }
}
