package com.jsmecommerce.portal3scanner.models.products

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.jsmecommerce.portal3scanner.models.general.OverviewCategory
import com.jsmecommerce.portal3scanner.models.general.Tag
import com.jsmecommerce.portal3scanner.utils.Api
import org.json.JSONObject

data class ScanProduct(
    val id: Int,
    val name: String,
    val tags: List<Tag>,
    val category: OverviewCategory,
    val created_at: String,
    val updated_at: String
) {
    companion object {
        fun fromJSON(obj: JSONObject): ScanProduct = ScanProduct(
            obj.getInt("id"),
            obj.getString("name"),
            Tag.fromJSONArray(obj.getJSONArray("tags")),
            OverviewCategory.fromJSON(obj.getJSONObject("category")),
            obj.getString("created_at"),
            obj.getString("updated_at")
        )

        fun findByBarcode(context: Context, barcode: String): ScanProduct? {
            val res = Api.Request(context, "/products/by_barcode")
                .setQuery("barcode", barcode)
                .exec()
            val obj = res.getJsonObject()
            if(res.hasError || obj == null)
                return null
            return fromJSON(obj)
        }
    }
}
