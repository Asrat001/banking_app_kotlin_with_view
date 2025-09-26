package com.example.bankingapp.data.models

data class TransactionItem(
    val id: String,
    val date: String,
    val description: String,
    val amount: Double,
    val type: String // "CREDIT" or "DEBIT"
)
