package com.example.scanlegal.data.remote.api

import com.example.scanlegal.data.remote.api.dto.auth.*
import com.example.scanlegal.data.remote.api.dto.billing.UsageResponse
import com.example.scanlegal.data.remote.api.dto.documents.*
import com.example.scanlegal.data.remote.api.dto.chat.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Auth endpoints
    @POST("api/v1/auth/register")
    suspend fun registerUser(@Body profile: ProfileCreate): Response<ProfileResponse>
    
    @POST("api/v1/auth/sync")
    suspend fun syncUser(): Response<Unit>
    
    @GET("api/v1/auth/me")
    suspend fun getCurrentUser(): Response<ProfileWithPreferences>
    
    @PATCH("api/v1/auth/profile")
    suspend fun updatePreferences(@Body preferences: UserPreferencesUpdate): Response<UserPreferencesResponse>
    
    @DELETE("api/v1/auth/account")
    suspend fun deleteAccount(): Response<Unit>
    
    // Billing endpoints
    @GET("api/v1/billing/usage")
    suspend fun getUsage(): Response<UsageResponse>
    
    @POST("api/v1/billing/webhook")
    suspend fun billingWebhook(@Body payload: Map<String, Any>): Response<Unit>
    
    // Documents endpoints
    @POST("api/v1/documents/analyse")
    suspend fun analyzeDocument(@Body request: DocumentAnalysisRequest): Response<Unit>
    
    @GET("api/v1/documents/{document_id}/status")
    suspend fun getDocumentStatus(@Path("document_id") documentId: String): Response<DocumentStatusResponse>
    
    @GET("api/v1/documents")
    suspend fun listDocuments(
        @Query("q") query: String? = null,
        @Query("document_type") documentType: String? = null,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): Response<List<DocumentResponse>>
    
    @GET("api/v1/documents/{document_id}")
    suspend fun getDocument(@Path("document_id") documentId: String): Response<DocumentDetailResponse>
    
    @PATCH("api/v1/documents/{document_id}")
    suspend fun updateDocument(
        @Path("document_id") documentId: String,
        @Body update: DocumentUpdate
    ): Response<DocumentResponse>
    
    @DELETE("api/v1/documents/{document_id}")
    suspend fun deleteDocument(@Path("document_id") documentId: String): Response<Unit>
    
    @POST("api/v1/documents/{document_id}/qa")
    suspend fun askDocumentQuestion(
        @Path("document_id") documentId: String,
        @Body request: DocumentQARequest
    ): Response<DocumentQAResponse>
    
    @POST("api/v1/documents/compare")
    suspend fun compareDocuments(@Body request: DocumentCompareRequest): Response<DocumentCompareResponse>
    
    // Chat endpoints
    @GET("api/v1/chat")
    suspend fun listChatSessions(): Response<List<ChatSessionResponse>>
    
    @POST("api/v1/chat")
    suspend fun createChatSession(@Body session: ChatSessionBase): Response<ChatSessionResponse>
    
    @GET("api/v1/chat/{session_id}")
    suspend fun getChatSession(@Path("session_id") sessionId: String): Response<ChatSessionWithMessages>
    
    @POST("api/v1/chat/{session_id}/messages")
    suspend fun sendMessage(
        @Path("session_id") sessionId: String,
        @Body message: ChatMessageCreate
    ): Response<ChatMessageResponse>
}
