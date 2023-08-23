package com.jsmecommerce.portal3scanner.datasource.portal3api.converters

import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Portal3Response
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
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

    class Portal3RestResponseBodyConverter<T>(private val converter: Converter<ResponseBody, Portal3Response<T>>): Converter<ResponseBody, T> {
        override fun convert(value: ResponseBody): T {
            val response = converter.convert(value)
            if(response == null || !response.success || response.data == null)
                throw Exception(response?.message ?: "Something went wrong")
            return response.data
        }

    }
}