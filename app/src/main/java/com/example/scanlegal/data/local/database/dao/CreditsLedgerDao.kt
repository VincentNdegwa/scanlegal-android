package com.example.scanlegal.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scanlegal.data.local.database.entities.CreditsLedgerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditsLedgerDao {
    
    @Query("SELECT * FROM credits_ledger WHERE userId = :userId ORDER BY createdAt DESC")
    fun getTransactionsByUserId(userId: String): Flow<List<CreditsLedgerEntity>>
    
    @Query("SELECT SUM(amountChanged) FROM credits_ledger WHERE userId = :userId")
    suspend fun getBalance(userId: String): Int?
    
    @Query("SELECT SUM(amountChanged) FROM credits_ledger WHERE userId = :userId AND transaction_type = 'CONSUMPTION' AND createdAt >= :startDate")
    suspend fun getConsumptionThisMonth(userId: String, startDate: String): Int?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: CreditsLedgerEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<CreditsLedgerEntity>)
    
    @Query("DELETE FROM credits_ledger WHERE id = :id")
    suspend fun deleteTransaction(id: Long)
    
    @Query("DELETE FROM credits_ledger WHERE userId = :userId")
    suspend fun deleteTransactionsByUserId(userId: String)
}
