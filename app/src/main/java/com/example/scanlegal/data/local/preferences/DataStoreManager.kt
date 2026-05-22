package com.example.scanlegal.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    object PreferencesKeys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val ACTIVE_JURISDICTION = stringPreferencesKey("active_jurisdiction")
        val PREFERRED_LANGUAGE = stringPreferencesKey("preferred_language")
        val USER_ID = stringPreferencesKey("user_id")
        val FIREBASE_TOKEN = stringPreferencesKey("firebase_token")
    }
    
    val onboardingCompleted: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
    }
    
    val activeJurisdiction: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ACTIVE_JURISDICTION]
    }
    
    val preferredLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.PREFERRED_LANGUAGE] ?: "en"
    }
    
    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ID]
    }
    
    val firebaseToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.FIREBASE_TOKEN]
    }
    
    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
    }
    
    suspend fun setActiveJurisdiction(jurisdiction: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACTIVE_JURISDICTION] = jurisdiction
        }
    }
    
    suspend fun setPreferredLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_LANGUAGE] = language
        }
    }
    
    suspend fun setUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }
    
    suspend fun setFirebaseToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIREBASE_TOKEN] = token
        }
    }
    
    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
