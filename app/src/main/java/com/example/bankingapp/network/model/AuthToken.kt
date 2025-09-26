package com.example.bankingapp.network.model



data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiry: Long // Epoch seconds
) {
    fun isAccessTokenExpired(): Boolean {
        return System.currentTimeMillis() / 1000 >= accessTokenExpiry - 300 // 5 min buffer
    }
}

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String, val code: Int? = null) : AuthResult<Nothing>()
    object NetworkError : AuthResult<Nothing>()
}