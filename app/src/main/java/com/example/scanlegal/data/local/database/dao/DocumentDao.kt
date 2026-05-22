package com.example.scanlegal.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.scanlegal.data.local.database.entities.DocumentAnalysisFlagEntity
import com.example.scanlegal.data.local.database.entities.DocumentDeadlineEntity
import com.example.scanlegal.data.local.database.entities.DocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    
    @Query("SELECT * FROM documents ORDER BY createdAt DESC")
    fun getAllDocuments(): Flow<List<DocumentEntity>>
    
    @Query("SELECT * FROM documents WHERE id = :id")
    suspend fun getDocumentById(id: String): DocumentEntity?
    
    @Query("SELECT * FROM documents WHERE title LIKE '%' || :query || '%' OR rawText LIKE '%' || :query || '%'")
    fun searchDocuments(query: String): Flow<List<DocumentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: DocumentEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocuments(documents: List<DocumentEntity>)
    
    @Update
    suspend fun updateDocument(document: DocumentEntity)
    
    @Query("DELETE FROM documents WHERE id = :id")
    suspend fun deleteDocument(id: String)
    
    @Query("DELETE FROM documents")
    suspend fun deleteAllDocuments()
    
    // Analysis flags
    @Query("SELECT * FROM document_analysis_flags WHERE documentId = :documentId")
    fun getAnalysisFlagsForDocument(documentId: String): Flow<List<DocumentAnalysisFlagEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysisFlag(flag: DocumentAnalysisFlagEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysisFlags(flags: List<DocumentAnalysisFlagEntity>)
    
    @Query("DELETE FROM document_analysis_flags WHERE documentId = :documentId")
    suspend fun deleteAnalysisFlagsForDocument(documentId: String)
    
    // Deadlines
    @Query("SELECT * FROM document_deadlines WHERE documentId = :documentId")
    fun getDeadlinesForDocument(documentId: String): Flow<List<DocumentDeadlineEntity>>
    
    @Query("SELECT * FROM document_deadlines WHERE deadlineDate >= :startDate ORDER BY deadlineDate ASC")
    fun getUpcomingDeadlines(startDate: String): Flow<List<DocumentDeadlineEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeadline(deadline: DocumentDeadlineEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeadlines(deadlines: List<DocumentDeadlineEntity>)
    
    @Update
    suspend fun updateDeadline(deadline: DocumentDeadlineEntity)
    
    @Query("DELETE FROM document_deadlines WHERE documentId = :documentId")
    suspend fun deleteDeadlinesForDocument(documentId: String)
}
