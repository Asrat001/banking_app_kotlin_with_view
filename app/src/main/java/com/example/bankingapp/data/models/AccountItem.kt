package com.example.bankingapp.data.models

data class AccountItem(
    val id: String,
    val accountType: String,
    val accountNumber: String,
    val balance: Double,
    val lastUpdated: String
)
