package com.bsoft.linkqueue.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LinkItem::class],version = 1,exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun linkDao() : LinkDao

}

class DatabaseBuilder(private val context : Context){
    private var instance : MyRoomDatabase? = null

    fun getInstance() : MyRoomDatabase{
        if (instance==null){
            synchronized(MyRoomDatabase::class.java){
                instance = Room.databaseBuilder(context,MyRoomDatabase::class.java,"MyDB").build()
            }
        }
        return instance!!
    }
}