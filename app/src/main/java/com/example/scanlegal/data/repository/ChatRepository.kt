package com.example.scanlegal.data.repository

import com.example.scanlegal.data.local.database.dao.ChatDao
import com.example.scanlegal.data.local.database.entities.ChatMessageEntity
import com.example.scanlegal.data.remote.api.ApiService
import com.example.scanlegal.data.remote.api.dto.chat.ChatMessageCreate
import com.example.scanlegal.data.remote.api.dto.chat.ChatMessageResponse
import com.example.scanlegal.data.remote.api.dto.chat.ChatSessionBase
import com.example.scanlegal.data.remote.api.dto.chat.ChatSessionResponse
import com.example.scanlegal.data.remote.api.dto.chat.ChatSessionWithMessages
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val apiService: ApiService,
    private val chatDao: ChatDao
) {
    
    suspend fun listChatSessions(): Result<List<ChatSessionResponse>> {
        return try {
            val response = apiService.listChatSessions()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to list sessions"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createChatSession(documentId: String): Result<ChatSessionResponse> {
        return try {
            val session = ChatSessionBase(documentId = documentId)
            val response = apiService.createChatSession(session)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create session"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getChatSession(sessionId: String): Result<ChatSessionWithMessages> {
        return try {
            val response = apiService.getChatSession(sessionId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get session"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun sendMessage(sessionId: String, content: String): Result<ChatMessageResponse> {
        return try {
            val message = ChatMessageCreate(content = content)
            val response = apiService.sendMessage(sessionId, message)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to send message"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Local database operations
    fun getMessagesForSession(sessionId: String): Flow<List<ChatMessageEntity>> {
        return chatDao.getMessagesForSession(sessionId)
    }
    
    suspend fun saveMessage(message: ChatMessageEntity) {
        chatDao.insertMessage(message)
    }
    
    suspend fun saveMessages(messages: List<ChatMessageEntity>) {
        chatDao.insertMessages(messages)
    }
    
    suspend fun deleteMessagesForSession(sessionId: String) {
        chatDao.deleteMessagesForSession(sessionId)
    }
}
