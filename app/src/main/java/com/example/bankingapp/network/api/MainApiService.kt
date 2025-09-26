package com.example.bankingapp.network.api

import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("user/info")
    suspend fun getUserInfo(): Response<String>
}