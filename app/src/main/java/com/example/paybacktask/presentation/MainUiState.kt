package com.example.paybacktask.presentation


import com.example.paybacktask.domain.entity.ImageViewEntities

data class MainUiState(
    val images: List<ImageViewEntities.ImagesDomainMappedItem> = listOf(),
    val errorMessage: String = "",
    val inProgress: Boolean = false
)
