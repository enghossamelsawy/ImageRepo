package com.example.paybacktask.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paybacktask.data.local.dao.ImagesDao
import com.example.paybacktask.data.remote.entity.Hit


@Database(
    entities = [Hit::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}

