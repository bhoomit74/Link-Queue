package com.bsoft.linkqueue.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LinkDao {

    @Query("SELECT * FROM linkitem")
    suspend fun getAllLinks() : List<LinkItem>

    @Insert
    suspend fun insertLink(linkItem: LinkItem)

    @Delete
    suspend fun deleteLink(linkItem: LinkItem)

}