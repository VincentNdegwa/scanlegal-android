package com.example.scanlegal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanlegal.data.local.database.dao.DocumentDao
import com.example.scanlegal.data.local.database.entities.DocumentEntity
import com.example.scanlegal.data.repository.BillingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val documentDao: DocumentDao,
    private val billingRepository: BillingRepository
) : ViewModel() {
    
    private val _recentDocuments = MutableStateFlow<List<DocumentEntity>>(emptyList())
    val recentDocuments: StateFlow<List<DocumentEntity>> = _recentDocuments.asStateFlow()
    
    private val _totalDocuments = MutableStateFlow(0)
    val totalDocuments: StateFlow<Int> = _totalDocuments.asStateFlow()
    
    private val _scansUsed = MutableStateFlow(0)
    val scansUsed: StateFlow<Int> = _scansUsed.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadHomeData()
    }
    
    private fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                // Load recent documents
                documentDao.getAllDocuments().collect { documents ->
                    _recentDocuments.value = documents.take(5)
                    _totalDocuments.value = documents.size
                }
                
                // Load usage stats
                val usage = billingRepository.getUsage()
                if (usage.isSuccess) {
                    _scansUsed.value = usage.getOrNull()?.scansUsedThisMonth ?: 0
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun refreshData() {
        loadHomeData()
    }
}
