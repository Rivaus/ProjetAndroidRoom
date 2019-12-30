package com.example.roomapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "task_table")
data class Task (@PrimaryKey() @Json(name = "id") val id : String,
                 @Json(name = "title") val title : String,
                 @Json(name = "description") val description : String,
                 var status : String = "local")