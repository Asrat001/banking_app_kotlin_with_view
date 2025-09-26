package com.example.bankingapp.network.model

data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val username: String,
    val password: String,
)
