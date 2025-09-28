package com.example.bankingapp.network.model
import com.example.bankingapp.data.models.AccountModel

data class PageAccountResponse(
    val content: List<AccountModel>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val sort: Sort,
    val offset: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class Sort(
    val sorted: Boolean,
    val unsorted: Boolean,
    val empty: Boolean
)
