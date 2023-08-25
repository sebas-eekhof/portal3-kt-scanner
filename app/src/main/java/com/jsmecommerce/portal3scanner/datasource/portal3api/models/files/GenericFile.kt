package com.jsmecommerce.portal3scanner.datasource.portal3api.models.files

import com.google.gson.annotations.SerializedName

data class GenericFile(
    val id: Int,
    @SerializedName("content_type")
    val contentType: String,
    val name: String,
    val uuid: String,
    val extension: String?
)
