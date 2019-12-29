package com.example.myapplicationempty

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName= "tasks_table")


data class Task(
    @Json(name = "id")
    @PrimaryKey
    var id : String,
    @Json(name = "title")
    var title : String,
    @Json(name = "description")
    var description : String)