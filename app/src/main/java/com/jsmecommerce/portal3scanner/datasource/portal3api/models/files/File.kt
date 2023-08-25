package com.jsmecommerce.portal3scanner.datasource.portal3api.models.files

import com.google.gson.annotations.SerializedName

data class File(
    val id: Int,
    @SerializedName("content_type")
    val contentType: String,
    val name: String,
    val uuid: String,
    val extension: String,
    @SerializedName("folder_id")
    val folderId: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
