package com.example.scanlegal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.scanlegal.data.local.database.dao.DocumentDao
import com.example.scanlegal.data.local.preferences.DataStoreManager
import com.example.scanlegal.data.repository.AuthRepository
import com.example.scanlegal.data.repository.BillingRepository
import com.example.scanlegal.presentation.navigation.ScanLegalNavGraph
import com.example.scanlegal.ui.theme.ScanlegalTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var dataStoreManager: DataStoreManager
    @Inject lateinit var authRepository: AuthRepository
    @Inject lateinit var documentDao: DocumentDao
    @Inject lateinit var billingRepository: BillingRepository
    @Inject lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScanlegalTheme {
                val navController = rememberNavController()
                ScanLegalNavGraph(
                    navController = navController,
                    dataStoreManager = dataStoreManager,
                    authRepository = authRepository,
                    documentDao = documentDao,
                    billingRepository = billingRepository,
                    firebaseAuth = firebaseAuth
                )
            }
        }
    }
}