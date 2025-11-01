package com.abbie.week78.data.container

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Artistcontainer {
    val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"

    private val client = OkHttpClient.Builder()
//        .addInterceptor(AuthInterceptor(API_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()
    fun getRetrofit(): Retrofit = retrofit
}

