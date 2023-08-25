package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Paginated
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.Order
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OrderStatus
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OverviewOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Portal3Orders {
    @GET("/orders")
    suspend fun all(
        @Query("status_id") statusId: Int? = null,
        @Query("store_id") storeId: Int? = null,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 20
    ): Response<Paginated<OverviewOrder>>

    @GET("/orders/statusses")
    suspend fun statusses(): Response<List<OrderStatus>>

    @GET("/orders/get")
    suspend fun get(
        @Query("order_id") orderId: Int
    ): Response<Order>
}