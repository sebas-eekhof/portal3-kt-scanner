package com.jsmecommerce.portal3scanner.datasource.portal3api.models

data class Test(
    val success: Boolean,
    val err: String?,
    val message: String?,
    val ms: Int,
    val mms: Int,
    val data: TestData
) {
    data class TestData(
        val ok: Boolean,
        val error: String
    )
}
