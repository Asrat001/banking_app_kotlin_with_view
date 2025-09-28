package com.example.bankingapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankingapp.data.BaseViewModel
import com.example.bankingapp.data.CoroutinesErrorHandler
import com.example.bankingapp.data.models.AccountModel
import com.example.bankingapp.data.repository.AccountRepository
import com.example.bankingapp.data.repository.TransactionRepository
import com.example.bankingapp.network.model.ApiResponse
import com.example.bankingapp.network.model.PageAccountResponse
import com.example.bankingapp.network.model.PageTransactionResponse
import com.example.bankingapp.network.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _accountResponse = MutableLiveData<ApiResponse<PageAccountResponse>>()
    private val _transactionResponse = MutableLiveData<ApiResponse<PageTransactionResponse>>()

    val accountResponse = _accountResponse
    val transactionResponse = _transactionResponse

    fun getAccounts(page: Int, coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(_accountResponse, coroutinesErrorHandler) {
            accountRepository.getAccounts(page, 10)
        }

    fun getAllAccountTransactions(
        accountIds: List<Int>,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) {
        baseRequest(_transactionResponse, coroutinesErrorHandler) {
            kotlinx.coroutines.flow.flow {
                val allTransactions = mutableListOf<Transaction>()
                coroutineScope {
                    val jobs = accountIds.map { accountId ->
                        async {
                            transactionRepository.getAllAccountTransaction(
                                accountId,
                                page = 0,
                                10
                            ).firstOrNull() { it is ApiResponse.Success }
                                ?.let { (it as ApiResponse.Success).data }
                        }
                    }

                    jobs.awaitAll()
                        .filterNotNull()
                        .forEach { pageResponse ->
                            val transactions = pageResponse.content.map { tr ->
                                Transaction(
                                    id = tr.id,
                                    accountId = tr.accountId,
                                    amount = tr.amount,
                                    type = tr.type,
                                    direction = tr.direction,
                                    description = tr.description ?: "",
                                    timestamp = tr.timestamp,
                                    date = tr.timestamp,
                                    relatedAccount = tr.relatedAccount
                                )
                            }
                            allTransactions.addAll(transactions)
                        }
                }

                allTransactions.sortByDescending { it.timestamp }

                emit(
                    ApiResponse.Success(
                        PageTransactionResponse(
                            totalPages = 1,
                            totalElements = allTransactions.size,
                            size = allTransactions.size,
                            content = allTransactions.map { it.toTransactionResponse() },
                            number = 0,
                            first = true,
                            last = true,
                            numberOfElements = allTransactions.size,
                            empty = allTransactions.isEmpty()
                        )
                    )
                )
            }
        }
    }


}