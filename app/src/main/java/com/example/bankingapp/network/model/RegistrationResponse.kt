package com.example.bankingapp.network.model

data class RegistrationResponse(
    val message: String,
    val username: Int,
    val userId: String,
    val initialAccountNumber: String
)
