package com.example.bankingapp.data.repository
import com.example.bankingapp.network.api.AuthApiService
import com.example.bankingapp.network.model.LoginRequest
import com.example.bankingapp.network.model.RegistrationRequest
import com.example.bankingapp.network.model.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(private  val authApiService: AuthApiService) {

    fun login(authRequest: LoginRequest)= apiRequestFlow {
        authApiService.login(authRequest)
    }

    fun register(registerationRequest: RegistrationRequest)=apiRequestFlow {
        authApiService.register(registerationRequest)
    }
}