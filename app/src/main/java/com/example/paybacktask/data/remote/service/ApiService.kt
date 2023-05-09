package com.example.paybacktask.data.remote.service

import com.example.paybacktask.data.remote.entity.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getImages(
        @Query("key") key: String,
        @Query("q") query: String
    ): Response<ImageResponse>
}