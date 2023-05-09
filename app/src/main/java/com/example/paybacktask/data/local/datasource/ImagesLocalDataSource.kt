package com.example.paybacktask.data.local.datasource

import com.example.paybacktask.data.local.dao.ImagesDao
import com.example.paybacktask.data.remote.entity.Hit
import javax.inject.Inject

class ImagesLocalDataSource @Inject constructor(
    private val imagesDao: ImagesDao
) {
    suspend fun saveImages(images: List<Hit>) {
        imagesDao.saveImages(images)
    }

    suspend fun fetchImages(searchString: String): List<Hit> {
        return imagesDao.fetchImages(searchString)
    }

    suspend fun getImageDetails(id: Int): Hit {
        return imagesDao.fetchImageDetails(id)
    }
}