package com.example.scanlegal.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scanlegal.data.local.database.entities.ChatSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatSessionDao {
    
    @Query("SELECT * FROM chat_sessions WHERE userId = :userId ORDER BY createdAt DESC")
    fun getSessionsByUserId(userId: String): Flow<List<ChatSessionEntity>>
    
    @Query("SELECT * FROM chat_sessions WHERE documentId = :documentId ORDER BY createdAt DESC")
    fun getSessionsByDocumentId(documentId: String): Flow<List<ChatSessionEntity>>
    
    @Query("SELECT * FROM chat_sessions WHERE id = :id")
    suspend fun getSessionById(id: String): ChatSessionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ChatSessionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<ChatSessionEntity>)
    
    @Query("DELETE FROM chat_sessions WHERE id = :id")
    suspend fun deleteSession(id: String)
    
    @Query("DELETE FROM chat_sessions WHERE documentId = :documentId")
    suspend fun deleteSessionsByDocumentId(documentId: String)
    
    @Query("DELETE FROM chat_sessions WHERE userId = :userId")
    suspend fun deleteSessionsByUserId(userId: String)
}
