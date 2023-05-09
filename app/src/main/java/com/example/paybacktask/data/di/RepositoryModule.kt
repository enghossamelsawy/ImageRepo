package com.example.paybacktask.data.di

import com.example.paybacktask.data.repository.ImageRepoImpl
import com.example.paybacktask.data.local.dao.ImagesDao
import com.example.paybacktask.data.local.datasource.ImagesLocalDataSource
import com.example.paybacktask.data.remote.service.ApiService
import com.example.paybacktask.data.remote.datasource.RemoteImagesDataSource
import com.example.paybacktask.domain.ImageRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteImagesDataSource {
        return RemoteImagesDataSource(apiService)
    }

    @Provides
    fun provideLocalImageDataSource(imagesDao: ImagesDao): ImagesLocalDataSource {
        return ImagesLocalDataSource(imagesDao)
    }

    @Provides
    fun provideImagesRepository(
        remoteDataSource: RemoteImagesDataSource,
        localDataSource: ImagesLocalDataSource
    ): ImageRepo = ImageRepoImpl(remoteDataSource, localDataSource)

}