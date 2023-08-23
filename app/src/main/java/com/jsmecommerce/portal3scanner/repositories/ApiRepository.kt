package com.jsmecommerce.portal3scanner.repositories

import android.content.Context
import com.jsmecommerce.portal3scanner.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.utils.Api

class ApiRepository(private val context: Context) {
    val orders = object {
        suspend fun all(
            status_id: Int? = null
        ): List<OverviewOrder> {
            val req = Api.Request(context, "/orders")
            if(status_id != null)
                req.setQuery("status_id", status_id.toString())
            val res = req.execCo()
            res.error?.let { throw it.Error }
            return OverviewOrder.fromJSONArray(res.getJsonArrayOrThrow())
        }
    }
}