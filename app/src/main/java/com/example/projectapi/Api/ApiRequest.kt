package com.example.projectapi.Api

import com.example.projectapi.Model.RickAndMorty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiRequest {
    @GET("/api/character") //Какой метод мы вызываем и по какому пути
    fun getCharacter(): Call<RickAndMorty> // Call - какой тип данных мы ожидаем

    @POST("api/SendCode")
    fun postEmail(@Header("User-email") email: String): Call<String>
}