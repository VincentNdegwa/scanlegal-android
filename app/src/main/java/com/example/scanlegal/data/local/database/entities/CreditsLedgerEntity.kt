package com.example.scanlegal.data.local.database.entities

import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "amount_changed")
    val amountChanged: Int,
    @ColumnInfo(name = "transaction_type")
    val transactionType: LedgerTransactionType,
    @ColumnInfo(name = "reference_id")
    val referenceId: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: String
)
