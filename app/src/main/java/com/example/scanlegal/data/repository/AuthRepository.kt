package com.example.scanlegal.data.repository

import com.example.scanlegal.data.local.preferences.DataStoreManager
import com.example.scanlegal.data.remote.api.ApiService
import com.example.scanlegal.data.remote.api.dto.auth.ProfileCreate
import com.example.scanlegal.data.remote.api.dto.auth.ProfileWithPreferences
import com.example.scanlegal.data.remote.api.dto.auth.UserPreferencesUpdate
import com.example.scanlegal.data.remote.api.dto.auth.UserPreferencesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {
    
    suspend fun registerUser(email: String, id: String, displayName: String? = null): Result<Unit> {
        return try {
            val profile = ProfileCreate(
                email = email,
                id = id,
                displayName = displayName
            )
            val response = apiService.registerUser(profile)
            if (response.isSuccessful) {
                dataStoreManager.setUserId(id)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun syncUser(): Result<Unit> {
        return try {
            val response = apiService.syncUser()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sync failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUser(): Result<ProfileWithPreferences> {
        return try {
            val response = apiService.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updatePreferences(preferences: UserPreferencesUpdate): Result<UserPreferencesResponse> {
        return try {
            val response = apiService.updatePreferences(preferences)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update preferences"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteAccount(): Result<Unit> {
        return try {
            val response = apiService.deleteAccount()
            if (response.isSuccessful) {
                dataStoreManager.clearAll()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete account"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getUserId() = dataStoreManager.userId
    fun getFirebaseToken() = dataStoreManager.firebaseToken
    
    suspend fun setFirebaseToken(token: String) {
        dataStoreManager.setFirebaseToken(token)
    }
}
