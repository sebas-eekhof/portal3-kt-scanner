package com.jsmecommerce.portal3scanner.models.general

import com.jsmecommerce.portal3scanner.utils.JSON
import org.json.JSONObject

data class App(
    val id: Int,
    val identifier: String,
    val name: String,
    val icon: String?
) {
    companion object {
        fun fromJSON(obj: JSONObject): App = App(
            obj.getInt("id"),
            obj.getString("identifier"),
            obj.getString("name"),
            JSON(obj).stringOrNull("icon")
        )
    }
}
