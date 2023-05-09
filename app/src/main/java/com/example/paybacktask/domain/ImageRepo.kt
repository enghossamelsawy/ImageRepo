package com.example.paybacktask.domain

import com.example.paybacktask.data.constant.NetworkStatus
import com.example.paybacktask.data.remote.entity.Hit


interface ImageRepo {

    suspend fun fetchImages(searchSting: String): NetworkStatus<List<Hit>>

    suspend fun fetchImage(id: Int): Hit

    suspend fun saveImages(imageDomainEntity: List<Hit>)
}