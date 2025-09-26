package com.example.bankingapp.network.model

data class RefreshTokenResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String
)
