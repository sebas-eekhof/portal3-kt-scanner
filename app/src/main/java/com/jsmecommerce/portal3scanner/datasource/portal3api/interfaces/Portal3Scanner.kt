package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Success
import retrofit2.http.Body
import retrofit2.http.GET

interface Portal3Scanner {
    data class ProbeBody(
        val battery: Int,
        val location: ProbeBodyLocation
    ) {
        data class ProbeBodyLocation(
            val lat: Float,
            val lon: Float,
            val alt: Float,
            val acc: Float
        )
    }
    @GET("/scanner/probe")
    suspend fun probe(@Body body: ProbeBody): Result<Success>
}