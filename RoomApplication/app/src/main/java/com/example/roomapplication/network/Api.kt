package com.example.roomapplication.network

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import com.example.roomapplication.data.Task
import com.example.roomapplication.data.UserInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object Api {
    private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxMTcsImV4cCI6MTYwOTMyNDA5OX0.FjddPwNmF15QMZm9D6yRz42-gppGRn3KDb1R6Xg8Z6s"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    // Instance de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun checkIfNetworkAvailable(activity : Activity?):Boolean{
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    val userService: UserService by lazy { retrofit.create(UserService::class.java) }

    val tasksService: TasksService by lazy { retrofit.create(TasksService::class.java) }
}

interface UserService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>
}

interface TasksService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<String>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}")
    suspend fun updateTask(@Body task: Task): Response<Task>
}