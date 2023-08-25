package com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces

import com.jsmecommerce.portal3scanner.datasource.portal3api.models.files.FilesCollection
import retrofit2.http.GET
import retrofit2.http.Query

interface Portal3Files {
    @GET("/files")
    suspend fun getByObject(
        @Query("object_type") objectType: String,
        @Query("object_id") objectId: Int
    ): Result<FilesCollection>
}