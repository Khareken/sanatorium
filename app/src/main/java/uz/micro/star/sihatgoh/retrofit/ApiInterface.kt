package com.example.e_lesson_1.retrofit

import retrofit2.Call
import retrofit2.http.*
import uz.micro.star.sihatgoh.models.SanatoriumFullData
import uz.micro.star.sihatgoh.models.SanatoriumTinyData

interface ApiInterface {

    @GET("sanatory")
    fun allSanatories(): Call<List<SanatoriumFullData>>

    @GET("sanatory/tiny")
    fun allSanatoriesTiny(): Call<List<SanatoriumTinyData>>
//    @POST("trainer")
//    fun addTriner(@Body trainer: TrainerData): Call<TrainerData>

//    @PUT("trainer/{id}")
//    fun updateTrainer(@Path("id") id: Int, @Body trainer: TrainerData): Call<TrainerData>
//
//    @DELETE("trainer/{id}")
//    fun deteteTrainerById(@Path("id") id: Int): Call<TrainerData>
}