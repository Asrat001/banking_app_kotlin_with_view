package com.example.bankingapp.adaptors.ui_state

import com.example.bankingapp.data.models.AccountModel

sealed class AccountUiState {
    data class Item(val account: AccountModel) : AccountUiState()
    object Loading : AccountUiState()
    data class Error(val message: String) : AccountUiState()
}
