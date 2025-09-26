package com.example.bankingapp.network.model


data class LoginResponse(
    val message: String,
    val username: String,
    val userId: Int,
    val accessToken: String,
    val refreshToken: String
)
