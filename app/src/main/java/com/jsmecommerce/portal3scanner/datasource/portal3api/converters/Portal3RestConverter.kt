package com.jsmecommerce.portal3scanner.datasource.portal3api.converters

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class Portal3RestConverter(gson: Gson): Converter.Factory() {
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)
    companion object {
        fun create(): Portal3RestConverter = Portal3RestConverter(Gson())
    }
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = Portal3Response::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? = gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return Portal3RestResponseBodyConverter(gsonConverter as Converter<ResponseBody, Portal3Response<Any>>)
    }

    data class Portal3Response<T>(
        val success: Boolean,
        val err: String?,
        val message: String?,
        val ms: Int,
        val mms: Int,
        val data: T?
    )

    class Portal3RestResponseBodyConverter<T>(private val converter: Converter<ResponseBody, Portal3Response<T>>): Converter<ResponseBody, T> {
        override fun convert(value: ResponseBody): T {
            val response = converter.convert(value)
            if(response == null || !response.success || response.data == null)
                throw Exception(response?.message ?: "Something went wrong")
            return response.data
        }

    }
}