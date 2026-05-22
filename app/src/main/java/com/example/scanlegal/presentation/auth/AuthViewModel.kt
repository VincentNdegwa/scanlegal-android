package com.example.scanlegal.presentation.auth

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanlegal.R
import com.example.scanlegal.data.local.preferences.DataStoreManager
import com.example.scanlegal.data.repository.AuthRepository
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    enum class AuthAction {
        LOGIN,
        REGISTER
    }
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        checkAuthState()
    }
    
    private fun checkAuthState() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            _authState.value = AuthState.Authenticated(currentUser.uid, currentUser.email)
            syncUserProfile()
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    fun signInWithGoogle(
        activity: Activity,
        credentialManager: CredentialManager,
        action: AuthAction
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val serverClientId = activity.getString(R.string.default_web_client_id)
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setServerClientId(serverClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .setAutoSelectEnabled(false)
                    .build()
                
                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()
                
                val result = credentialManager.getCredential(activity, request)

                val googleIdToken = extractGoogleIdToken(result)
                if (googleIdToken.isNullOrBlank()) {
                    _errorMessage.value = "Authentication failed: unsupported credential"
                    _authState.value = AuthState.Unauthenticated
                    return@launch
                }

                authenticateWithGoogleIdToken(googleIdToken, action)
            } catch (e: GetCredentialException) {
                _errorMessage.value = "Google sign-in failed: ${e.message}"
                _authState.value = AuthState.Unauthenticated
            } catch (e: Exception) {
                _errorMessage.value = "Authentication failed: ${e.message}"
                _authState.value = AuthState.Unauthenticated
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun extractGoogleIdToken(result: GetCredentialResponse): String? {
        val credential = result.credential
        return when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    GoogleIdTokenCredential.createFrom(credential.data).idToken
                } else {
                    null
                }
            }
            else -> null
        }
    }

    fun signInWithEmail(
        email: String,
        password: String,
        onInvalidInput: (String) -> Unit
    ) {
        if (email.isBlank()) {
            onInvalidInput("Enter your email")
            return
        }
        if (password.isBlank()) {
            onInvalidInput("Enter your password")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val authResult = withContext(Dispatchers.IO) {
                    Tasks.await(firebaseAuth.signInWithEmailAndPassword(email, password))
                }

                val user = authResult.user
                if (user == null) {
                    _errorMessage.value = "Sign-in failed"
                    _authState.value = AuthState.Unauthenticated
                    return@launch
                }

                finishFirebaseAuth(user, AuthAction.LOGIN, null)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Email sign-in failed", e)
                _errorMessage.value = mapAuthExceptionToMessage(e)
                _authState.value = AuthState.Unauthenticated
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun registerWithEmail(
        fullName: String,
        email: String,
        password: String,
        onInvalidInput: (String) -> Unit
    ) {
        if (fullName.isBlank()) {
            onInvalidInput("Enter your full name")
            return
        }
        if (email.isBlank()) {
            onInvalidInput("Enter your email")
            return
        }
        if (password.length < 8) {
            onInvalidInput("Password must be at least 8 characters")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val authResult = withContext(Dispatchers.IO) {
                    Tasks.await(firebaseAuth.createUserWithEmailAndPassword(email, password))
                }

                val user = authResult.user
                if (user == null) {
                    _errorMessage.value = "Sign-up failed"
                    _authState.value = AuthState.Unauthenticated
                    return@launch
                }

                val profileRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()

                withContext(Dispatchers.IO) {
                    Tasks.await(user.updateProfile(profileRequest))
                }

                finishFirebaseAuth(user, AuthAction.REGISTER, fullName)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Email sign-up failed", e)
                _errorMessage.value = mapAuthExceptionToMessage(e)
                _authState.value = AuthState.Unauthenticated
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendPasswordReset(
        email: String,
        onInvalidInput: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        if (email.isBlank()) {
            onInvalidInput("Enter your email")
            return
        }

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    Tasks.await(firebaseAuth.sendPasswordResetEmail(email))
                }
                onSuccess()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Password reset failed", e)
                _errorMessage.value = "Password reset failed"
            }
        }
    }

    private fun mapAuthExceptionToMessage(e: Exception): String {
        return when (e) {
            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
            is FirebaseAuthInvalidUserException -> "Account not found"
            is FirebaseAuthUserCollisionException -> "Email already in use"
            is FirebaseAuthWeakPasswordException -> "Password is too weak"
            else -> "Authentication failed"
        }
    }

    private suspend fun authenticateWithGoogleIdToken(googleIdToken: String, action: AuthAction) {
        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        val authResult = withContext(Dispatchers.IO) {
            Tasks.await(firebaseAuth.signInWithCredential(firebaseCredential))
        }

        val user = authResult.user
        if (user == null) {
            _errorMessage.value = "Authentication failed"
            _authState.value = AuthState.Unauthenticated
            return
        }

        finishFirebaseAuth(user, action, null)
    }

    private suspend fun finishFirebaseAuth(
        user: FirebaseUser,
        action: AuthAction,
        displayNameOverride: String?
    ) {
        val idTokenResult = withContext(Dispatchers.IO) {
            Tasks.await(user.getIdToken(true))
        }
        val idToken = idTokenResult.token ?: ""

        dataStoreManager.setFirebaseToken(idToken)
        dataStoreManager.setUserId(user.uid)

        val displayName = displayNameOverride ?: user.displayName
        val backendResult = when (action) {
            AuthAction.REGISTER -> {
                val registerResult = authRepository.registerUser(
                    email = user.email ?: "",
                    id = user.uid,
                    displayName = displayName
                )
                if (registerResult.isSuccess) {
                    authRepository.syncUser()
                } else {
                    authRepository.syncUser()
                }
            }
            AuthAction.LOGIN -> authRepository.syncUser()
        }

        if (backendResult.isSuccess) {
            _authState.value = AuthState.Authenticated(user.uid, user.email)
            syncUserProfile()
        } else {
            _errorMessage.value = "Sign-in failed"
            _authState.value = AuthState.Authenticated(user.uid, user.email)
        }
    }
    
    private fun syncUserProfile() {
        viewModelScope.launch {
            try {
                authRepository.syncUser()
                val profile = authRepository.getCurrentUser()
                if (profile.isSuccess) {
                    val prefs = profile.getOrNull()?.preferences
                    prefs?.let {
                        dataStoreManager.setActiveJurisdiction(it.activeJurisdiction)
                        dataStoreManager.setPreferredLanguage(it.preferredOutputLanguage)
                        dataStoreManager.setOnboardingCompleted(it.onboardingCompleted)
                    }
                }
            } catch (e: Exception) {
                // Sync failed, but user is still authenticated
            }
        }
    }
    
    fun signOut() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            dataStoreManager.clearAll()
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}

sealed class AuthState {
    object Initial : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val userId: String, val email: String?) : AuthState()
}
