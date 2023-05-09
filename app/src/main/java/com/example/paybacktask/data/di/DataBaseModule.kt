package com.example.paybacktask.data.di

import android.content.Context
import androidx.room.Room
import com.example.paybacktask.data.local.datasource.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    private const val DATABASE_NAME = "images"

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideImagesDao(db: AppDatabase) = db.imagesDao()
}