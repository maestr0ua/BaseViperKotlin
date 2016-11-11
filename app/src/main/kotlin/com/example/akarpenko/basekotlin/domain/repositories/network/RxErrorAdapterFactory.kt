package com.example.akarpenko.basekotlin.domain.repositories.network

import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.HttpException
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import rx.functions.Func1

import com.example.akarpenko.basekotlin.domain.repositories.network.RxErrorAdapterFactory.Kind.HTTP
import com.example.akarpenko.basekotlin.domain.repositories.network.RxErrorAdapterFactory.Kind.NETWORK
import com.example.akarpenko.basekotlin.domain.repositories.network.RxErrorAdapterFactory.Kind.UNEXPECTED

class RxErrorAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJavaCallAdapterFactory

    init {
        original = RxJavaCallAdapterFactory.create()
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*> {
        return RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit))
    }

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    internal enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    private class RxCallAdapterWrapper(private val retrofit: Retrofit, private val wrapped: CallAdapter<*>) : CallAdapter<Observable<*>> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        @SuppressWarnings("unchecked")
        override fun <R> adapt(call: Call<R>): Observable<*> {
            return (wrapped.adapt(call) as Observable<*>).onErrorResumeNext(Func1{ throwable ->
                throwable.printStackTrace()
                Observable.error(this@RxCallAdapterWrapper.asRetrofitException(throwable))
            })
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {

            if (throwable is SocketTimeoutException) {
                return RetrofitException.socketError(throwable)
            }

            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit)
            }
            // A network error happened
            if (throwable is IOException) {
                return RetrofitException.networkError(throwable)
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.unexpectedError(throwable)
        }
    }

    class RetrofitException internal constructor(message: String?,
                                                 /**
                                                  * The request URL which produced the error.
                                                  */
                                                 val url: String?,
                                                 /**
                                                  * Response object containing status code, headers, body, etc.
                                                  */
                                                 val response: Response<*>?,
                                                 /**
                                                  * The event kind which triggered this error.
                                                  */
                                                 val kind: Kind,

                                                 exception: Throwable?,
                                                 /**
                                                  * The Retrofit this request was executed on
                                                  */
                                                 val retrofit: Retrofit?) : RuntimeException(message, exception) {

        /**
         * HTTP response body converted to specified `type`. `null` if there is no
         * response.

         * @throws IOException if unable to convert the body to the specified `type`.
         */
        @Throws(IOException::class)
        fun <T> getErrorBodyAs(type: Class<T>): T? {
            if (response == null || response.errorBody() == null) {
                return null
            }
            val converter = retrofit!!.responseBodyConverter<T>(type, arrayOfNulls<Annotation>(0))
            return converter.convert(response.errorBody())
        }

        companion object {

            internal fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
                val message = response.code().toString() + " " + response.message()
                return RetrofitException(message, url, response, HTTP, null, retrofit)
            }

            internal fun networkError(exception: IOException): RetrofitException {
                return RetrofitException(exception.message, null, null, NETWORK, exception, null)
            }

            internal fun socketError(exception: SocketTimeoutException): RetrofitException {
                return RetrofitException(exception.message, null, null, NETWORK, exception, null)
            }

            internal fun unexpectedError(exception: Throwable): RetrofitException {
                return RetrofitException(exception.message, null, null, UNEXPECTED, exception, null)
            }
        }
    }

    companion object {

        fun create(): CallAdapter.Factory {
            return RxErrorAdapterFactory()
        }
    }
}