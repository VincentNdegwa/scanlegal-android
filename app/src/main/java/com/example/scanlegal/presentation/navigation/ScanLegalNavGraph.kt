package com.example.scanlegal.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scanlegal.data.local.preferences.DataStoreManager
import com.example.scanlegal.data.repository.AuthRepository
import com.example.scanlegal.data.repository.BillingRepository
import com.example.scanlegal.data.local.database.dao.DocumentDao
import com.example.scanlegal.presentation.auth.AuthState
import com.example.scanlegal.presentation.auth.AuthViewModel
import com.example.scanlegal.presentation.auth.LoginScreen
import com.example.scanlegal.presentation.auth.OnboardingScreen
import com.example.scanlegal.presentation.auth.RegisterScreen
import com.example.scanlegal.presentation.home.HomeScreen
import com.example.scanlegal.presentation.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Scan : Screen("scan")
    object Upload : Screen("upload")
}

@Composable
fun ScanLegalNavGraph(
    navController: NavHostController,
    dataStoreManager: DataStoreManager,
    authRepository: AuthRepository,
    documentDao: DocumentDao,
    billingRepository: BillingRepository,
    firebaseAuth: FirebaseAuth
) {
    val authViewModel = remember(authRepository, dataStoreManager, firebaseAuth) {
        AuthViewModel(authRepository, dataStoreManager, firebaseAuth)
    }
    val authState by authViewModel.authState.collectAsState()
    val onboardingCompleted by dataStoreManager.onboardingCompleted.collectAsState(initial = false)
    
    NavHost(
        navController = navController,
        startDestination = determineStartDestination(authState, onboardingCompleted)
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onAuthSuccess = {
                    if (onboardingCompleted) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screen.Register.route)
                },
                viewModel = authViewModel
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onAuthSuccess = {
                    if (onboardingCompleted) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onSignInClick = {
                    navController.popBackStack()
                },
                viewModel = authViewModel
            )
        }
        
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                dataStoreManager = dataStoreManager
            )
        }
        
        composable(Screen.Home.route) {
            val homeViewModel = remember { HomeViewModel(documentDao, billingRepository) }
            HomeScreen(
                onScanClick = {
                    navController.navigate(Screen.Scan.route)
                },
                onUploadClick = {
                    navController.navigate(Screen.Upload.route)
                },
                onDocumentClick = { documentId ->
                    // Navigate to document detail - will be implemented later
                },
                viewModel = homeViewModel
            )
        }
        
        composable(Screen.Scan.route) {
            // Camera screen - will be implemented in Phase 5
            androidx.compose.material3.Text("Camera Screen")
        }
        
        composable(Screen.Upload.route) {
            // Upload screen - will be implemented later
            androidx.compose.material3.Text("Upload Screen")
        }
    }
}

private fun determineStartDestination(
    authState: AuthState,
    onboardingCompleted: Boolean
): String {
    return when (authState) {
        is AuthState.Authenticated -> {
            if (onboardingCompleted) Screen.Home.route else Screen.Onboarding.route
        }
        else -> Screen.Login.route
    }
}
