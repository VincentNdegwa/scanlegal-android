package com.example.scanlegal.data.local.database.converter

import androidx.room.TypeConverter
import com.example.scanlegal.data.local.database.entities.LedgerTransactionType
import com.example.scanlegal.domain.model.enums.MessageSender
import com.example.scanlegal.domain.model.enums.ProcessingStatus
import com.example.scanlegal.domain.model.enums.RiskSeverity

class Converters {
    
    @TypeConverter
    fun fromRiskSeverity(value: RiskSeverity): String {
        return value.name
    }
    
    @TypeConverter
    fun toRiskSeverity(value: String): RiskSeverity {
        return RiskSeverity.valueOf(value)
    }
    
    @TypeConverter
    fun fromProcessingStatus(value: ProcessingStatus): String {
        return value.name
    }
    
    @TypeConverter
    fun toProcessingStatus(value: String): ProcessingStatus {
        return ProcessingStatus.valueOf(value)
    }
    
    @TypeConverter
    fun fromMessageSender(value: MessageSender): String {
        return value.name
    }
    
    @TypeConverter
    fun toMessageSender(value: String): MessageSender {
        return MessageSender.valueOf(value)
    }
    
    @TypeConverter
    fun fromLedgerTransactionType(value: LedgerTransactionType): String {
        return value.name
    }
    
    @TypeConverter
    fun toLedgerTransactionType(value: String): LedgerTransactionType {
        return LedgerTransactionType.valueOf(value)
    }
}
