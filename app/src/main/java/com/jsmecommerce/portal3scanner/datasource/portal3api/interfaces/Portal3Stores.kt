package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Paginated
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.stores.Store
import retrofit2.Response
import retrofit2.http.GET

interface Portal3Stores {
    @GET("/stores")
    suspend fun all(): Response<Paginated<Store>>

    @GET("/stores/all")
    suspend fun allWithoutPagination(): Response<List<Store>>
}