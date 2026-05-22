package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class LedgerTransactionType {
    SUP_ALLOC,
    PURCHASE,
    CONSUMPTION,
    REFUND,
    PROMOTION
}

@Entity(tableName = "credits_ledger")
data class CreditsLedgerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val amountChanged: Int,
    val transactionType: LedgerTransactionType,
    val referenceId: String? = null,
    val createdAt: String
)
