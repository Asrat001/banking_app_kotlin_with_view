package com.example.bankingapp.network.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class TransactionModel(
    @Json(name = "id")
    val id: Int,

    @Json(name = "accountId")
    val accountId: Int,

    @Json(name = "amount")
    val amount: Double,

    @Json(name = "type")
    val type: TransactionType,

    @Json(name = "direction")
    val direction: TransactionDirection,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "timestamp")
    val timestamp: Date,

    @Json(name = "balanceAfter")
    val balanceAfter: Double? = null,

    @Json(name = "toAccount")
    val toAccount: String? = null,

    @Json(name = "fromAccount")
    val fromAccount: String? = null,

    @Json(name = "relatedAccount")
    val relatedAccount: String? = null
)



@JsonClass(generateAdapter = true)
enum class TransactionType {
    @Json(name = "FUND_TRANSFER") FUND_TRANSFER,
    @Json(name = "TELLER_TRANSFER") TELLER_TRANSFER,
    @Json(name = "ATM_WITHDRAWAL") ATM_WITHDRAWAL,
    @Json(name = "TELLER_DEPOSIT") TELLER_DEPOSIT,
    @Json(name = "BILL_PAYMENT") BILL_PAYMENT,
    @Json(name = "ACCESS_FEE") ACCESS_FEE,
    @Json(name = "PURCHASE") PURCHASE,
    @Json(name = "REFUND") REFUND,
    @Json(name = "INTEREST_EARNED") INTEREST_EARNED,
    @Json(name = "LOAN_PAYMENT") LOAN_PAYMENT,
    @Json(name = "SALARY") SALARY,
    @Json(name = "GROCERY") GROCERY,
    @Json(name = "CARD_PAYMENT") CARD_PAYMENT
}

@JsonClass(generateAdapter = true)
enum class TransactionDirection {
    @Json(name = "DEBIT") DEBIT,
    @Json(name = "CREDIT") CREDIT
}
