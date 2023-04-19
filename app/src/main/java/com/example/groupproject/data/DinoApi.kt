package com.example.groupproject.data
import retrofit2.http.GET

interface DinoApi {
    @GET("test")
    suspend fun doNetworkCall()
}