package com.jsmecommerce.portal3scanner.utils

import androidx.compose.ui.text.toLowerCase
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Api {
    class Request(private val path: String) {
        private var method: RequestMethod = RequestMethod.GET
        private var query: HashMap<String, String> = HashMap()
        private var body: String? = null
        private var namespace: Namespace = Namespace.API

        fun setNamespace(namespace: Namespace): Request {
            this.namespace = namespace
            return this
        }

        fun setMethod(method: RequestMethod): Request {
            this.method = method;
            return this
        }

        fun setQuery(key: String, value: String): Request {
            this.query[key] = value
            return this
        }

        fun setBody(body: String): Request {
            this.body = body;
            return this
        }

        fun setBody(body: JSONObject): Request {
            this.body = body.toString();
            return this
        }

        fun setBody(body: JSONArray): Request {
            this.body = body.toString();
            return this
        }

        private fun toURL(): URL {
            var urlString = ("https://${namespace.toString().lowercase()}.portal3.nl" + (if (path.startsWith("/")) path else ("/$path")))
            val query: ArrayList<String> = ArrayList();
            for(key in this.query.keys)
                query.add("${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(this.query[key], "UTF-8")}")
            if(query.size != 0)
                urlString = "$urlString?${query.joinToString("&")}"
            return URL(urlString)
        }

        fun exec(): Response {
            val url = toURL()
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = method.toString()
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                setRequestProperty("Accept", "application/json")
                if(body != null) {
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(body)
                    wr.close()
                }
                println("\nSent '${method}' request to URL : $url; Response Code : $responseCode")
                println(headerFields.toString())
                if(responseCode < 200 || responseCode >= 300)
                    return Response(
                        success = false,
                        error = Error("REQUEST_ERROR_$responseCode", responseMessage),
                        ms = 0,
                        mms = 0,
                        data = null
                    )
                inputStream.bufferedReader().use { output ->
                    val rawRes = output.lines().toArray().joinToString()
                    output.close()
                    return try {
                        val res = JSONObject(rawRes)
                        val success = res.optBoolean("success", false)
                        Response(
                            success,
                            error = (if (!success) Error(
                                err = res.optString("err", "UNKNOWN_ERROR"),
                                message = res.optString("message", "Unknown error occurred")
                            ) else null),
                            ms = res.optInt("ms", 0),
                            mms = res.optInt("mms", 0),
                            data = res
                        )
                    } catch(e: Exception) {
                        Response(
                            success = false,
                            error = Error("PARSE_ERROR", e.message ?: "Unknown error occurred"),
                            ms = 0,
                            mms = 0,
                            data = null
                        )
                    }
                }
            }
        }
    }

    class Response(private val success: Boolean, val error: Api.Error?, val ms: Int, val mms: Int, private val data: JSONObject?) {
        val hasError: Boolean = (!success)

        override fun toString(): String {
            return data?.toString() ?: "ERR_RES_NO_DATA"
        }

        fun isJsonArray(): Boolean {
            if(data == null || data.isNull("data"))
                return false
            return try {
                data.getJSONArray("data")
                true
            } catch(e: Exception) {
                false
            }
        }

        fun getJsonArray(): JSONArray? {
            if(data == null || data.isNull("data"))
                return null
            return try {
                data.getJSONArray("data")
            } catch(e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun isJsonObject(): Boolean {
            if(data == null || data.isNull("data"))
                return false
            return try {
                data.getJSONObject("data")
                true
            } catch(e: Exception) {
                false
            }
        }

        fun getJsonObject(): JSONObject? {
            if(data == null || data.isNull("data"))
                return null
            return try {
                data.getJSONObject("data")
            } catch(e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    class Error(val err: String, val message: String)

    enum class Namespace {
        REST,
        API
    }

    enum class RequestMethod {
        GET,
        POST,
        PUT,
        PATCH,
DELETE
    }
}