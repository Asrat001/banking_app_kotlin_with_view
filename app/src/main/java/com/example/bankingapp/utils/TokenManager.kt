package com.example.bankingapp.utils
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import androidx.core.content.edit


class TokenManager(private val context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "user_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val TOKEN_KEY = "jwt_token"

    suspend fun saveToken(token: String) {
        sharedPreferences.edit { putString(TOKEN_KEY, token) }
    }

    fun getToken(): Flow<String?> {
        return flow {
            emit(sharedPreferences.getString(TOKEN_KEY, null))
        }
    }

    suspend fun deleteToken() {
        sharedPreferences.edit { remove(TOKEN_KEY) }
    }
}