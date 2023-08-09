package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.JSON
import com.jsmecommerce.portal3scanner.utils.toJSONObjectList
import org.json.JSONArray
import org.json.JSONObject

data class Event(
    val id: Int,
    val event: String,
    val user: EventUser?,
    val user_name: String?,
    val dispatched_at: String
) {
    data class EventUser(
        val id: Int,
        val full_name: String,
        val jdenticon: String,
        val email: String
    ) {
        companion object {
            fun fromJSON(obj: JSONObject): EventUser = EventUser(
                obj.getInt("id"),
                obj.getString("full_name"),
                obj.getString("jdenticon"),
                obj.getString("email")
            )
        }
    }
    companion object {
        fun fromJSON(obj: JSONObject): Event = Event(
            obj.getInt("id"),
            obj.getString("event"),
            if (obj.isNull("user")) null else EventUser.fromJSON(obj.getJSONObject("user")),
            JSON(obj).stringOrNull("user_name"),
            obj.getString("dispatched_at")
        )
        fun fromJSONArray(obj: JSONArray): List<Event> = obj.toJSONObjectList().map { fromJSON(it) }
    }
}
