package com.example.e_lesson_1.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCLient {
    private const val BASE_URL = "https://sanatoriya.herokuapp.com/api/"
    var retrofit: Retrofit? = null

    fun refreshToken() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}