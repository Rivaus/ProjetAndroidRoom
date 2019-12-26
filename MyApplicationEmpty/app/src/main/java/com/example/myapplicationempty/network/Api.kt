package com.example.myapplicationempty.network

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplicationempty.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

class Api(private val context: Context) {
    companion object {
        private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"
        private const val TOKEN = "votre token"
        lateinit var INSTANCE: Api
    }

    fun getToken() : String{
        return context.getSharedPreferences("TEST", Context.MODE_PRIVATE).getString(SHARED_PREF_TOKEN_KEY, "")!!
    }

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val okHttpClient by lazy {

        //private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjozLCJleHAiOjE2MDY4OTc5MTZ9.H4RHvH9Vtn9vYfW_kbMuIHO2DWtL8UgfILhbK-63lWg!"


        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    //.addHeader("Authorization", "Bearer $TOKEN")
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userService: UserService by lazy { retrofit.create(UserService::class.java) }

    val tasksService:TasksService by lazy { retrofit.create(TasksService::class.java)}
}

interface UserService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>

    @Multipart
    @PATCH("users/update_avatar")
    suspend fun updateAvatar(@Part avatar: MultipartBody.Part): Response<UserInfo>

    @POST("users/login")
    suspend fun login(@Body user: LoginForm): Response<TokenResponse>

    @POST("users/sign_up")
    suspend fun signUp(@Body user: SignUpForm): Response<TokenResponse>
}

interface TasksService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<String>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body task: Task): Response<Task>
}