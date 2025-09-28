package com.example.bankingapp.ui.auth.register

import androidx.lifecycle.MutableLiveData
import com.example.bankingapp.data.BaseViewModel
import com.example.bankingapp.data.CoroutinesErrorHandler
import com.example.bankingapp.data.repository.AuthRepository
import com.example.bankingapp.network.model.ApiResponse
import com.example.bankingapp.network.model.RegistrationRequest
import com.example.bankingapp.network.model.RegistrationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    private val _registerResponse = MutableLiveData<ApiResponse<RegistrationResponse>>()
    val registerResponse = _registerResponse

    fun register(
        registrationRequest: RegistrationRequest,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(_registerResponse, coroutinesErrorHandler) {
        authRepository.register(registrationRequest)
    }


}