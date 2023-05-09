package com.example.paybacktask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paybacktask.data.remote.entity.Hit


@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(images: List<Hit>)

    @Query("SELECT * FROM ImagesDomainItem WHERE tags LIKE '%' || :searchString || '%'")
    suspend fun fetchImages(searchString: String): List<Hit>


    @Query("SELECT * FROM ImagesDomainItem WHERE id = :itemId LIMIT 1")
    suspend fun fetchImageDetails(itemId: Int): Hit


}