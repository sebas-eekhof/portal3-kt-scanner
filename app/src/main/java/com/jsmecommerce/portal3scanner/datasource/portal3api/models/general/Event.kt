package com.jsmecommerce.portal3scanner.datasource.portal3api.models.general

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int,
    val event: String,
    val user: EventUser?,
    @SerializedName("user_name")
    val userName: String?,
    @SerializedName("dispatched_at")
    val dispatchedAt: String
) {
    data class EventUser(
        val id: Int,
        @SerializedName("full_name")
        val fullName: String,
        val jdenticon: String,
        val email: String
    )
}