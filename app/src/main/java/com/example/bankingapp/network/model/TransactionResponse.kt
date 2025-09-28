package com.example.bankingapp.network.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date


@JsonClass(generateAdapter = true)
data class TransactionResponse(
    @Json(name = "id")
    val id: Int,

    @Json(name = "amount")
    val amount: Double,

    @Json(name = "type")
    val type: TransactionType = TransactionType.FUND_TRANSFER, // fallback

    @Json(name = "direction")
    val direction: TransactionDirection = TransactionDirection.DEBIT, // fallback

    @Json(name = "timestamp")
    val timestamp: Date,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "relatedAccount")
    val relatedAccount: String? = null,

    @Json(name = "accountId")
    val accountId: Int
)