package com.example.bankingapp.network.model

data class LoginRequest(
    val username: String,
    val passwordHash: String
)
