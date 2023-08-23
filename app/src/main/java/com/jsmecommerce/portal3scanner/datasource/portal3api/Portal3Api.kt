package com.jsmecommerce.portal3scanner.datasource.portal3api

import android.content.Context
import com.jsmecommerce.portal3scanner.datasource.portal3api.converters.Portal3RestConverter
import com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces.Portal3Auth
import com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces.Portal3Orders
import com.jsmecommerce.portal3scanner.datasource.portal3api.interfaces.Portal3Stores
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Device
import com.jsmecommerce.portal3scanner.utils.Static
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class Portal3Api(private val retrofit: Retrofit) {
    val auth get() = retrofit.create(Portal3Auth::class.java)
    val orders get() = retrofit.create(Portal3Orders::class.java)
    val stores get() = retrofit.create(Portal3Stores::class.java)

    companion object {
        private fun getClient(context: Context): OkHttpClient {
            val client = OkHttpClient.Builder()
            val auth = Auth(context)
            val device = Device(context)
            client.addInterceptor {
                val req = it.request()
                    .newBuilder()
                    .addHeader("x-hwid", device.getHWID())
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Accept", "application/json")
                val jwt = auth.getJWT()
                if(jwt != null)
                    req.addHeader("Authorization", "Bearer $jwt")
                val resReq = req.build()
                it.proceed(resReq)
            }
            return client.build()
        }
        fun getInstance(context: Context): Portal3Api {
            return Portal3Api(
                Retrofit.Builder()
                    .baseUrl(Static.API_BASE_URL)
                    .client(getClient(context))
                    .addConverterFactory(Portal3RestConverter.create())
                    .build()
            )
        }
    }
}