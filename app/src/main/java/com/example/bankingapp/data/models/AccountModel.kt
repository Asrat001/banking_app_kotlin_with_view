package com.example.bankingapp.data.models

data class AccountModel(
    val id: Int,
    val accountNumber: String,
    val accountType: String,
    val balance: Double,
    val userId: Int
){

    fun  toAccountItem(): AccountItem{
        return AccountItem(
            id=id,
            accountNumber=accountNumber,
            accountType = accountType,
            balance = balance,
            lastUpdated = "29/25"

        )
    }
}