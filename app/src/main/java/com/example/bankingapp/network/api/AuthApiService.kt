package com.example.bankingapp.network.api
import com.example.bankingapp.network.model.LoginRequest
import com.example.bankingapp.network.model.LoginResponse
import com.example.bankingapp.network.model.RefreshTokenResponse
import com.example.bankingapp.network.model.RegistrationRequest
import com.example.bankingapp.network.model.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body auth: LoginRequest): Response<LoginResponse>

    @POST(value = "auth/register")
    suspend fun register(@Body auth: RegistrationRequest): Response<RegistrationResponse>

    @GET("auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): Response<RefreshTokenResponse>
}