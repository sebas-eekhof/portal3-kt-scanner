package com.jsmecommerce.portal3scanner.models.orders

import com.jsmecommerce.portal3scanner.enums.PriceMarginTypeEnum
import com.jsmecommerce.portal3scanner.enums.VatEnum
import com.jsmecommerce.portal3scanner.models.general.OverviewCategory
import com.jsmecommerce.portal3scanner.models.general.Tag
import com.jsmecommerce.portal3scanner.models.products.DefaultSupplierProduct
import com.jsmecommerce.portal3scanner.utils.getIntOrNull
import com.jsmecommerce.portal3scanner.utils.getStringOrNull
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class OrderRule(
    val id: Int,
    val dropshipped: Boolean,
    val parent_id: Int?,
    val title: String,
    val description: String?,
    val quantity: Int,
    val price: Double,
    val discount: Double,
    val vat: VatEnum,
    val is_group_product: Boolean,
    val product: OrderRuleProduct?,
    val scans_amount: Int,
    val stock: Int
) {
    val total_ex = quantity.toDouble() * (price - discount)
    data class OrderRuleProduct(
        val id: Int,
        val name: String,
        val price: Double,
        val vat: VatEnum,
        val tags: List<Tag>,
        val category: OverviewCategory,
        val price_margin_type: PriceMarginTypeEnum,
        val price_margin_amount: Double,
        val default_supplier_product: DefaultSupplierProduct?
    ) {
        companion object {
            fun fromJSON(obj: JSONObject): OrderRuleProduct = OrderRuleProduct(
                obj.getInt("id"),
                obj.getString("name"),
                obj.getDouble("price"),
                VatEnum.fromString(obj.getString("vat")),
                Tag.fromJSONArray(obj.getJSONArray("tags")),
                OverviewCategory.fromJSON(obj.getJSONObject("category")),
                PriceMarginTypeEnum.fromString(obj.getString("price_margin_type")),
                obj.getDouble("price_margin_amount"),
                if (obj.isNull("default_supplier_product")) null else DefaultSupplierProduct.fromJSON(obj.getJSONObject("default_supplier_product"))
            )
        }
    }
    companion object {
        fun fromJSON(obj: JSONObject): OrderRule = OrderRule(
            obj.getInt("id"),
            obj.getBoolean("dropshipped"),
            obj.getIntOrNull("parent_id"),
            obj.getString("title"),
            obj.getStringOrNull("description"),
            obj.getInt("quantity"),
            obj.getDouble("price"),
            obj.getDouble("discount"),
            VatEnum.fromString(obj.getString("vat")),
            obj.getBoolean("is_group_product"),
            if (obj.isNull("product")) null else OrderRuleProduct.fromJSON(obj.getJSONObject("product")),
            obj.getInt("scans_amount"),
            obj.getInt("stock")
        )
        fun fromJSONArray(obj: JSONArray): List<OrderRule> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
