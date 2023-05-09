package com.example.paybacktask.domain.usecases

import com.example.paybacktask.data.constant.Constants
import com.example.paybacktask.data.constant.NetworkStatus
import com.example.paybacktask.data.remote.entity.getImagesDomainMappedItem
import com.example.paybacktask.domain.ImageRepo
import com.example.paybacktask.domain.entity.ImageViewEntities
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class ImagesUseCase @Inject constructor(
    private val imageRepo: ImageRepo
) {

    operator fun invoke(searchString: String) = channelFlow {
        val imageViewEntities = when (val fetchImages = imageRepo.fetchImages(searchString)) {
            is NetworkStatus.Success -> {
                if (fetchImages.data.isNullOrEmpty()) {
                    ImageViewEntities.Failure(Constants.UNKNOWN_NETWORK_EXCEPTION)
                } else {
                    ImageViewEntities.ImageDomainEntity(fetchImages.data.map { it.getImagesDomainMappedItem() })
                }
            }

            is NetworkStatus.Error -> ImageViewEntities.Failure(Constants.UNKNOWN_NETWORK_EXCEPTION)
        }
        send(imageViewEntities)
    }

    fun fetchImage(id: Int) =
        channelFlow {
            val fetchImage = imageRepo.fetchImage(id)
            val imageViewEntities = if (fetchImage != null) {
                ImageViewEntities.ImageDomainEntity(listOf(fetchImage.getImagesDomainMappedItem()))
            } else {
                ImageViewEntities.Failure(Constants.UNKNOWN_NETWORK_EXCEPTION)
            }

            send(imageViewEntities)
        }
}