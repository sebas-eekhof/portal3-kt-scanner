package com.jsmecommerce.portal3scanner.utils

import android.content.Context
import com.jsmecommerce.portal3scanner.models.User
import org.json.JSONObject
import java.lang.Exception
import java.util.Base64
import java.util.Date

class Auth(val context: Context) {
    val db = Database(context)
    fun isLoggedIn(): Boolean {
        return getUser() != null
    }

    fun getUser(): User? {
        val jwt = db.get("jwt") ?: return null
        val split = jwt.split(".")
        if(split.size != 3) return null
        try {
            val data = JSONObject(String(Base64.getDecoder().decode(split[1])))
            if(
                !data.has("id") ||
                !data.has("full_name") ||
                !data.has("email") ||
                !data.has("exp")
            ) return null
            val expireDate = Date(data.getLong("exp") * 1000)
            if(expireDate < Date()) return null
            return User(
                email = data.getString("email"),
                fullName = data.getString("full_name"),
                id = data.getInt("id"),
                jdenticon = data.getString("jdenticon"),
                expire = expireDate
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}