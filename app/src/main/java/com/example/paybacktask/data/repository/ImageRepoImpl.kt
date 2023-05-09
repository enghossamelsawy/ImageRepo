package com.example.paybacktask.data.repository


import com.example.paybacktask.data.constant.Constants
import com.example.paybacktask.data.constant.NetworkStatus
import com.example.paybacktask.data.local.datasource.ImagesLocalDataSource
import com.example.paybacktask.data.remote.datasource.RemoteImagesDataSource
import com.example.paybacktask.data.remote.entity.Hit
import com.example.paybacktask.domain.ImageRepo


class ImageRepoImpl(
    private val remoteImagesDataSource: RemoteImagesDataSource,
    private val localDataSource: ImagesLocalDataSource
) :
    ImageRepo {
    override suspend fun fetchImages(searchSting: String): NetworkStatus<List<Hit>> {
        val images = remoteImagesDataSource.getImages(searchSting)

        val hits = images.data?.hits
        return when (images) {
            is NetworkStatus.Success -> NetworkStatus.Success(hits)
            is NetworkStatus.Error -> {
                val fetchImages = localDataSource.fetchImages(searchSting)
                if (fetchImages.isNullOrEmpty()) return NetworkStatus.Error(errorMessage = Constants.GENERAL_ERROR)
                else NetworkStatus.Success(fetchImages)
            }

        }
    }

    override suspend fun fetchImage(id: Int): Hit {
        return localDataSource.getImageDetails(id)
    }


    override suspend fun saveImages(imageDomainEntity: List<Hit>) {
        localDataSource.saveImages(imageDomainEntity)
    }


}

