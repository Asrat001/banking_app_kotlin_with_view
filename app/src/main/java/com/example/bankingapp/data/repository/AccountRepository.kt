package com.example.bankingapp.data.repository

import com.example.bankingapp.network.api.MainApiService
import com.example.bankingapp.network.model.CreateAccountRequest
import com.example.bankingapp.network.model.TransferRequest
import com.example.bankingapp.network.model.apiRequestFlow
import javax.inject.Inject

class AccountRepository @Inject constructor(private  val  mainApiService: MainApiService){

    fun  createAccount(createAccountRequest: CreateAccountRequest)= apiRequestFlow {
        mainApiService.createAccount(createAccountRequest)
    }

    fun getAccounts(page:Int,size:Int)=apiRequestFlow {
        mainApiService.getAccounts(page,size)
    }

    fun accountTransfer(transferRequest: TransferRequest)=apiRequestFlow {
        mainApiService.transfer(transferRequest)
    }
}
