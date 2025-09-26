package com.example.bankingapp.network.model
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorString = error.charStream().readText() // convert Reader to String
                    val parsedError = adapter.fromJson(errorString)
                    if (parsedError != null) {
                    emit(ApiResponse.Failure(parsedError?.message?:"Unknow Error", parsedError.code))
                } else {
                    emit(ApiResponse.Failure("Unknown error", "UNKNOWN"))
                }
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString(), "UNKNOWN"))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", "UNKNOWN"))
}.flowOn(Dispatchers.IO)