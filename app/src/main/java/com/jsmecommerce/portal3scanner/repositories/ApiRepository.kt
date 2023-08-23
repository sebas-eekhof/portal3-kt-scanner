package com.jsmecommerce.portal3scanner.repositories

import android.content.Context
import com.jsmecommerce.portal3scanner.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.utils.Api

class ApiRepository(private val context: Context) {
    val orders = object {
        suspend fun all(
            status_id: Int? = null,
            store_id: Int? = null
        ): Api.ParsedResponse<List<OverviewOrder>> {
            val req = Api.Request(context, "/orders")
                .setQuery("status_id", status_id)
                .setQuery("store_id", store_id)
            if(status_id != null)
                req.setQuery("status_id", status_id.toString())
            return req.execCo().toParsedResponse { OverviewOrder.fromJSONArray(getJsonArrayOrThrow()) }
        }
    }
}