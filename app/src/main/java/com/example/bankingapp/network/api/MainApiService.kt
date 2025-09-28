package com.example.bankingapp.network.api

import com.example.bankingapp.data.models.AccountModel
import com.example.bankingapp.network.model.CreateAccountRequest
import com.example.bankingapp.network.model.PageAccountResponse
import com.example.bankingapp.network.model.PageTransactionResponse
import com.example.bankingapp.network.model.TransactionModel
import com.example.bankingapp.network.model.TransferRequest
import com.example.bankingapp.network.model.TransferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiService {
    @GET("user/info")
    suspend fun getUserInfo(): Response<String>

    //accounts
    @GET("accounts")
    suspend fun getAccounts(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<PageAccountResponse>

    @POST("accounts/create")
    suspend fun createAccount(@Body request: CreateAccountRequest): Response<AccountModel>

    @POST("accounts/transfer")
    suspend fun transfer(@Body request: TransferRequest): Response<TransferResponse>

    //transactions
    @GET("transactions/{accountId}")
    suspend fun getTransactions(
        @Path("accountId") accountId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<List<TransactionModel>>

    @GET("transactions/{accountId}")
    suspend fun getTransactionHistory(
        @Path("accountId") accountId: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<PageTransactionResponse>

}