package com.example.bankingapp.adaptors.ui_state

import com.example.bankingapp.network.model.TransactionResponse

sealed class TransactionUiState {
    data class Item(val transaction: TransactionResponse) : TransactionUiState()
    object Loading : TransactionUiState()
    data class Error(val message: String) : TransactionUiState()
}
