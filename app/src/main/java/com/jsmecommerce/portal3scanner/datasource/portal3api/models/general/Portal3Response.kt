package com.jsmecommerce.portal3scanner.datasource.portal3api.models.general

data class Portal3Response<T>(
    val success: Boolean,
    val err: String?,
    val message: String?,
    val ms: Int,
    val mms: Int,
    val data: T?
)
