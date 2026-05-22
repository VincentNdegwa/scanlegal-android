package com.example.scanlegal.data.repository

import com.example.scanlegal.data.local.database.dao.DocumentDao
import com.example.scanlegal.data.local.database.entities.DocumentAnalysisFlagEntity
import com.example.scanlegal.data.local.database.entities.DocumentDeadlineEntity
import com.example.scanlegal.data.local.database.entities.DocumentEntity
import com.example.scanlegal.data.remote.api.ApiService
import com.example.scanlegal.data.remote.api.dto.documents.DocumentAnalysisRequest
import com.example.scanlegal.data.remote.api.dto.documents.DocumentCompareRequest
import com.example.scanlegal.data.remote.api.dto.documents.DocumentCompareResponse
import com.example.scanlegal.data.remote.api.dto.documents.DocumentDetailResponse
import com.example.scanlegal.data.remote.api.dto.documents.DocumentQARequest
import com.example.scanlegal.data.remote.api.dto.documents.DocumentQAResponse
import com.example.scanlegal.data.remote.api.dto.documents.DocumentResponse
import com.example.scanlegal.data.remote.api.dto.documents.DocumentStatusResponse
import com.example.scanlegal.data.remote.api.dto.documents.DocumentUpdate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepository @Inject constructor(
    private val apiService: ApiService,
    private val documentDao: DocumentDao
) {
    
    fun getAllDocuments(): Flow<List<DocumentEntity>> {
        return documentDao.getAllDocuments()
    }
    
    suspend fun getDocumentById(id: String): DocumentEntity? {
        return documentDao.getDocumentById(id)
    }
    
    fun searchDocuments(query: String): Flow<List<DocumentEntity>> {
        return documentDao.searchDocuments(query)
    }
    
    suspend fun analyzeDocument(request: DocumentAnalysisRequest): Result<Unit> {
        return try {
            val response = apiService.analyzeDocument(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Analysis failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getDocumentStatus(documentId: String): Result<DocumentStatusResponse> {
        return try {
            val response = apiService.getDocumentStatus(documentId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get status"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun fetchDocuments(): Result<List<DocumentResponse>> {
        return try {
            val response = apiService.listDocuments()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch documents"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun fetchDocumentDetail(documentId: String): Result<DocumentDetailResponse> {
        return try {
            val response = apiService.getDocument(documentId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch document detail"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateDocument(documentId: String, update: DocumentUpdate): Result<DocumentResponse> {
        return try {
            val response = apiService.updateDocument(documentId, update)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update document"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteDocument(documentId: String): Result<Unit> {
        return try {
            val response = apiService.deleteDocument(documentId)
            if (response.isSuccessful) {
                documentDao.deleteDocument(documentId)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete document"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun askDocumentQuestion(documentId: String, question: String): Result<DocumentQAResponse> {
        return try {
            val request = DocumentQARequest(question = question)
            val response = apiService.askDocumentQuestion(documentId, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to ask question"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun compareDocuments(documentIdA: String, documentIdB: String): Result<DocumentCompareResponse> {
        return try {
            val request = DocumentCompareRequest(documentIdA, documentIdB)
            val response = apiService.compareDocuments(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to compare documents"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Local database operations
    suspend fun saveDocument(document: DocumentEntity) {
        documentDao.insertDocument(document)
    }
    
    suspend fun saveDocuments(documents: List<DocumentEntity>) {
        documentDao.insertDocuments(documents)
    }
    
    suspend fun saveAnalysisFlags(documentId: String, flags: List<DocumentAnalysisFlagEntity>) {
        documentDao.deleteAnalysisFlagsForDocument(documentId)
        documentDao.insertAnalysisFlags(flags)
    }
    
    suspend fun saveDeadlines(documentId: String, deadlines: List<DocumentDeadlineEntity>) {
        documentDao.deleteDeadlinesForDocument(documentId)
        documentDao.insertDeadlines(deadlines)
    }
    
    fun getAnalysisFlags(documentId: String) = documentDao.getAnalysisFlagsForDocument(documentId)
    fun getDeadlines(documentId: String) = documentDao.getDeadlinesForDocument(documentId)
    fun getUpcomingDeadlines(startDate: String) = documentDao.getUpcomingDeadlines(startDate)
}
