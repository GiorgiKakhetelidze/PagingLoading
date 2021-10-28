package com.example.homework14.network

import com.example.homework14.model.UserPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun getUsers(@Query("page") page : Int) : Response<UserPage>
}