package com.example.paybacktask.domain.entity

sealed class ImageViewEntities {

    data class ImageDomainEntity(
        val images: List<ImagesDomainMappedItem>
    ) : ImageViewEntities()

    data class ImagesDomainMappedItem(
        val id: Int,
        val tags: List<String>,
        val statistics: Statistics,
        val userInfo: UserInfo,
        val imageThumbnailUrl: String?,
        val largeImageURL: String?,
        val type: String?
    )

    data class Failure(val errorText: String) : ImageViewEntities()
}

data class Statistics(
    val comments: Int,
    val downloads: Int,
    val views: Int,
    val likes: Int
)

data class UserInfo(
    val user: String?,
    val userId: Int?,
    val userImageURL: String?
)