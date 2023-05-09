package com.example.paybacktask.data.remote.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paybacktask.domain.entity.ImageViewEntities
import com.example.paybacktask.domain.entity.Statistics
import com.example.paybacktask.domain.entity.UserInfo
import com.example.paybacktask.utils.getCommaSeparatedList
import com.google.gson.annotations.SerializedName


@Entity(tableName = "ImagesDomainItem")
data class Hit(
    @field:SerializedName("comments")
    val comments: Int?,
    @field:SerializedName("downloads")
    val downloads: Int?,
    @field:SerializedName("fullHDURL")
    val fullHDURL: String?,
    @field:SerializedName("id")
    @PrimaryKey
    val id: Int,
    @field:SerializedName("imageHeight")
    val imageHeight: Int?,
    @field:SerializedName("imageSize")
    val imageSize: Int?,
    @field:SerializedName("imageURL")
    val imageURL: String?,
    @field:SerializedName("imageWidth")
    val imageWidth: Int?,
    @field:SerializedName("largeImageURL")
    val largeImageURL: String?,
    @field:SerializedName("likes")
    val likes: Int?,
    @field:SerializedName("pageURL")
    val pageURL: String?,
    @field:SerializedName("previewHeight")
    val previewHeight: Int?,
    @field:SerializedName("previewURL")
    val previewURL: String?,
    @field:SerializedName("previewWidth")
    val previewWidth: Int?,
    @field:SerializedName("tags")
    val tags: String?,
    @field:SerializedName("type")
    val type: String?,
    @field:SerializedName("user")
    val user: String?,
    @field:SerializedName("user_id")
    val userId: Int?,
    @field:SerializedName("userImageURL")
    val userImageURL: String?,
    @field:SerializedName("views")
    val views: Int?,
    @field:SerializedName("webformatHeight")
    val webformatHeight: Int?,
    @field:SerializedName("webformatURL")
    val webformatURL: String?,
    @field:SerializedName("webformatWidth")
    val webformatWidth: Int?
)

fun Hit.getImagesDomainMappedItem() =
    ImageViewEntities.ImagesDomainMappedItem(
        id = id,
        tags = tags?.getCommaSeparatedList() ?: emptyList(),
        statistics = Statistics(comments ?: 0, downloads ?: 0, views ?: 0, likes ?: 0),
        userInfo = UserInfo(user, userId, userImageURL),
        imageThumbnailUrl = previewURL,
        largeImageURL = largeImageURL,
        type = type
    )
