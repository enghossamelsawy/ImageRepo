package com.example.paybacktask.data.remote.entity


import com.example.paybacktask.data.remote.entity.Hit
import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @field:SerializedName("hits")
    val hits: List<Hit>?,
    @field:SerializedName("total")
    val total: Int?,
    @field:SerializedName("totalHits")
    val totalHits: Int?
)