package com.abbie.week78.data.container

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherServerContainer {
    val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val API_KEY = "9aedd10161b79db9eee645517f080da6"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(API_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()
}


class AuthInterceptor(private val bearerToken: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(request)
    }
}