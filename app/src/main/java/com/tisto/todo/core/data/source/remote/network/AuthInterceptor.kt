package com.tisto.todo.core.data.source.remote.network

import com.tisto.todo.util.Constant.AUTH_TOKEN
import com.tisto.todo.util.getToken
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                .header(AUTH_TOKEN, getToken())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}