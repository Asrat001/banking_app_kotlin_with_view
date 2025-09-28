package com.example.bankingapp.network.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PageTransactionResponse(
    @Json(name = "totalPages")
    val totalPages: Int,

    @Json(name = "totalElements")
    val totalElements: Int,

    @Json(name = "size")
    val size: Int,

    @Json(name = "content")
    val content: List<TransactionResponse>,

    @Json(name = "number")
    val number: Int,

    @Json(name = "first")
    val first: Boolean,

    @Json(name = "last")
    val last: Boolean,

    @Json(name = "numberOfElements")
    val numberOfElements: Int,

    @Json(name = "empty")
    val empty: Boolean
)