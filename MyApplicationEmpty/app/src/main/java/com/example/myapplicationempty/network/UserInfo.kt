package com.example.myapplicationempty.network

import com.squareup.moshi.Json
import okhttp3.MultipartBody

data class UserInfo(
    @Json(name = "email")
    val email: String,
    @Json(name = "firstname")
    val firstName: String,
    @Json(name = "lastname")
    val lastName: String,
    @Json(name = "avatar")
    val avatar: String?
)