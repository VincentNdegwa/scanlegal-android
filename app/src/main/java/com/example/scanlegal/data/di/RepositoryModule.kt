package com.example.scanlegal.data.di

import com.example.scanlegal.data.repository.AuthRepository
import com.example.scanlegal.data.repository.BillingRepository
import com.example.scanlegal.data.repository.ChatRepository
import com.example.scanlegal.data.repository.DocumentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: com.example.scanlegal.data.remote.api.ApiService,
        dataStoreManager: com.example.scanlegal.data.local.preferences.DataStoreManager
    ): AuthRepository {
        return AuthRepository(apiService, dataStoreManager)
    }
    
    @Provides
    @Singleton
    fun provideDocumentRepository(
        apiService: com.example.scanlegal.data.remote.api.ApiService,
        documentDao: com.example.scanlegal.data.local.database.dao.DocumentDao
    ): DocumentRepository {
        return DocumentRepository(apiService, documentDao)
    }
    
    @Provides
    @Singleton
    fun provideChatRepository(
        apiService: com.example.scanlegal.data.remote.api.ApiService,
        chatDao: com.example.scanlegal.data.local.database.dao.ChatDao
    ): ChatRepository {
        return ChatRepository(apiService, chatDao)
    }
    
    @Provides
    @Singleton
    fun provideBillingRepository(
        apiService: com.example.scanlegal.data.remote.api.ApiService
    ): BillingRepository {
        return BillingRepository(apiService)
    }
}
