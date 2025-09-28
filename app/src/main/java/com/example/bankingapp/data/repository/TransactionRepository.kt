package com.example.bankingapp.data.repository

import com.example.bankingapp.network.api.MainApiService
import com.example.bankingapp.network.model.apiRequestFlow
import javax.inject.Inject

class TransactionRepository @Inject constructor(private  val mainApiService: MainApiService) {

    fun getTransaction(accId:String,page:Int,size:Int)= apiRequestFlow {
        mainApiService.getTransactions(accId,page,size)
    }

    fun getAllAccountTransaction(accId: Int, page:Int, pageSize: Int)=apiRequestFlow {
        mainApiService.getTransactionHistory(accId,page,pageSize)
    }

}