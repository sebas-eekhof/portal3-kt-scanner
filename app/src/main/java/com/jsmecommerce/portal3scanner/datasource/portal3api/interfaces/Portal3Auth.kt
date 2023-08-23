package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import retrofit2.Response
import retrofit2.http.POST

interface Portal3Auth {
    @POST("/auth/login")
    fun login(): Response<Any>
}