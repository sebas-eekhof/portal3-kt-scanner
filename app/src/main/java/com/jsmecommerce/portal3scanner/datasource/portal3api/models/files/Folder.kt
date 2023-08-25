package com.jsmecommerce.portal3scanner.datasource.portal3api.models.files

import com.google.gson.annotations.SerializedName
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.enums.ColorEnum

data class Folder(
    val id: Int,
    val name: String,
    @SerializedName("parent_id")
    val parentId: Int?,
    val color: ColorEnum,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
