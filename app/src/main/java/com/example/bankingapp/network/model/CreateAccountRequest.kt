package com.example.bankingapp.network.model

data class CreateAccountRequest(
    val accountType: String,
    val initialBalance: Double
)
