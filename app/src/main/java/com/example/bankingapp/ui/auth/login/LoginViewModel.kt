package com.example.bankingapp.ui.auth.login
import androidx.lifecycle.MutableLiveData
import com.example.bankingapp.data.BaseViewModel
import com.example.bankingapp.data.CoroutinesErrorHandler
import com.example.bankingapp.data.repository.AuthRepository
import com.example.bankingapp.network.model.ApiResponse
import com.example.bankingapp.network.model.LoginRequest
import com.example.bankingapp.network.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private  val authRepository: AuthRepository)  : BaseViewModel() {
    private  val _loginResponse= MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResponse=_loginResponse;


    fun login(loginRequest: LoginRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(loginRequest)
    }
}