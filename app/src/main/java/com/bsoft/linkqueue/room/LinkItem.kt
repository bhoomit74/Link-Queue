package com.bsoft.linkqueue.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "LinkItem")
class LinkItem(
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "link")
    var link: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}