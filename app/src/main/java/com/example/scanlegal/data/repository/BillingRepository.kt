package com.example.scanlegal.data.repository

import com.example.scanlegal.data.remote.api.ApiService
import com.example.scanlegal.data.remote.api.dto.billing.UsageResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun getUsage(): Result<UsageResponse> {
        return try {
            val response = apiService.getUsage()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get usage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
