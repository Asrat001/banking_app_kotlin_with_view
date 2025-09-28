package com.example.bankingapp.network.model
import java.util.Date


data class Transaction(
    val id: Int,
    val accountId: Int,
    val amount: Double,
    val type: TransactionType,
    val direction: TransactionDirection,
    val description: String,
    val timestamp: Date,
    val date: Date,
    val relatedAccount: String? = null,
    val balanceAfter: Double? = null,
    val toAccount: String? = null,
    val fromAccount: String? = null
){
    fun toTransactionResponse(): TransactionResponse {
        return TransactionResponse(
            id = id,
            amount = amount,
            type = type,
            direction = direction,
            timestamp = timestamp,
            description = description,
            relatedAccount = relatedAccount,
            accountId = accountId
        )
    }
}
