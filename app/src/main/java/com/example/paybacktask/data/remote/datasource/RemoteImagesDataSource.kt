package com.example.paybacktask.data.remote.datasource

import com.example.paybacktask.BuildConfig
import com.example.paybacktask.data.remote.entity.ImageResponse
import com.example.paybacktask.data.constant.NetworkStatus
import com.example.paybacktask.data.remote.service.ApiService
import com.example.paybacktask.utils.safeApiCall
import javax.inject.Inject

class RemoteImagesDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getImages(imageQuery: String): NetworkStatus<ImageResponse> =
        safeApiCall { apiService.getImages(BuildConfig.API_KEY, imageQuery) }
}