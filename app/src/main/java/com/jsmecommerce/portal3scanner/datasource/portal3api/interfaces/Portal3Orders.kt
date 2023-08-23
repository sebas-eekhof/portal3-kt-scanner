package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import retrofit2.Response
import retrofit2.http.GET

interface Portal3Orders {
    @GET("/orders")
    fun all(): Response<Any>
}