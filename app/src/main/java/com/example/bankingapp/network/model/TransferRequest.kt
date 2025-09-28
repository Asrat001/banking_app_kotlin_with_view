package com.example.bankingapp.network.model

data class TransferRequest(
    val fromAccountNumber: String,
    val toAccountNumber: String,
    val amount: Double
)
