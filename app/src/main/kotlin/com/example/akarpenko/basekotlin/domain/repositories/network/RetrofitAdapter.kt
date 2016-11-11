package com.example.akarpenko.basekotlin.domain.repositories.network

import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.utils.Logger
import okhttp3.*
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitAdapter : IRestAdapter {

    override fun <T> createApi(clazz: Class<T>, base_url: String): T {

        val client = OkHttpClient.Builder()
                .cookieJar(MyCookieJar())
                .addNetworkInterceptor(LoggingInterceptor())
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxErrorAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
                .create(clazz)
    }

    class MyCookieJar : CookieJar {

        private var cookies: List<Cookie>? = null

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            if (cookies != null)
                return cookies!!.toList()
            return ArrayList()
        }
    }

    inner class LoggingInterceptor : Interceptor {

        private val F_BREAK = " %n"
        private val F_URL = " %s"
        private val F_TIME = " in %.1fms %n"
        private val F_HEADERS = "%s"
        private val F_RESPONSE = "Response: %d"
        private val F_BODY = "body: %s"

        private val F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK
        private val F_REQUEST_WITHOUT_BODY = F_URL + F_BREAK + F_HEADERS
        private val F_REQUEST_WITH_BODY = F_URL + F_BREAK + F_HEADERS + F_BODY + F_BREAK
        private val F_RESPONSE_WITH_BODY = F_RESPONSE + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER

        private fun stringifyRequestBody(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body().writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                return "did not work"
            }

        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            if (!App.instance.isDebuggable()) {
                return chain.proceed(chain.request())
            }

            val request = chain.request()

            Logger.d("Intercept called")
            when (request.method()) {
                "GET" -> Logger.d(String.format(Locale.getDefault(), "GET " + F_REQUEST_WITHOUT_BODY, request.url(),
                        request.headers()))
                "PATCH" -> Logger.d(String.format(Locale.getDefault(), "PATCH " + F_REQUEST_WITH_BODY, request.url(),
                        request.headers(), stringifyRequestBody(request)))
                "POST" -> Logger.d(String.format(Locale.getDefault(), "POST " + F_REQUEST_WITH_BODY, request.url(),
                        request.headers(), stringifyRequestBody(request)))
                "PUT" -> Logger.d(String.format(Locale.getDefault(), "PUT " + F_REQUEST_WITH_BODY, request.url(), request.headers(), request.body().toString()))
            }

            val t1 = System.nanoTime()
            val response = chain.proceed(request)
            val t2 = System.nanoTime()

            var contentType: MediaType? = null
            var bodyBytes: ByteArray? = null

            if (response.body() != null) {
                contentType = response.body().contentType()
                bodyBytes = response.body().bytes()
            }

            val diffTime = (t2 - t1) / 1e6
            when (request.method()) {
                "GET" -> Logger.d(String.format(Locale.getDefault(), F_RESPONSE_WITH_BODY,
                        response.code(), diffTime, response.headers(), stringifyResponseBody(bodyBytes)))
                "PATCH" -> Logger.d(String.format(Locale.getDefault(), F_RESPONSE_WITH_BODY,
                        response.code(), diffTime, response.headers(), stringifyResponseBody(bodyBytes)))
                "POST" -> Logger.d(String.format(Locale.getDefault(), F_RESPONSE_WITH_BODY,
                        response.code(), diffTime, response.headers(), stringifyResponseBody(bodyBytes)))
                "PUT" -> Logger.d(String.format(Locale.getDefault(), F_RESPONSE_WITH_BODY,
                        response.code(), diffTime, response.headers(), stringifyResponseBody(bodyBytes)))
            }
            if (response.body() != null && bodyBytes != null) {
                val body = ResponseBody.create(contentType, bodyBytes)
                return response.newBuilder().body(body).build()
            } else {
                return response
            }
        }

        internal fun stringifyResponseBody(body: ByteArray?): String {
            try {
                if (body != null) {
                    return String(body)
                } else return "body is NULL"
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return ""
        }

    }

}
