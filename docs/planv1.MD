# ScanLegal Implementation Plan v1

## Project Structure (Single Module)

```
scanlegal/
├── app/
│   ├── src/main/java/com/example/scanlegal/
│   │   ├── MainActivity.kt
│   │   ├── ui/
│   │   │   ├── theme/
│   │   │   │   ├── Theme.kt
│   │   │   │   ├── Color.kt
│   │   │   │   └── Type.kt
│   │   │   ├── auth/
│   │   │   │   ├── login/
│   │   │   │   │   ├── LoginScreen.kt
│   │   │   │   │   └── LoginViewModel.kt
│   │   │   │   ├── onboarding/
│   │   │   │   │   ├── OnboardingScreen.kt
│   │   │   │   │   └── OnboardingViewModel.kt
│   │   │   │   └── AuthViewModel.kt
│   │   │   ├── camera/
│   │   │   │   ├── CameraScreen.kt
│   │   │   │   └── CameraViewModel.kt
│   │   │   ├── documents/
│   │   │   │   ├── DocumentListScreen.kt
│   │   │   │   ├── DocumentListViewModel.kt
│   │   │   │   ├── DocumentDetailScreen.kt
│   │   │   │   └── DocumentDetailViewModel.kt
│   │   │   ├── chat/
│   │   │   │   ├── ChatScreen.kt
│   │   │   │   └── ChatViewModel.kt
│   │   │   ├── profile/
│   │   │   │   ├── ProfileScreen.kt
│   │   │   │   └── ProfileViewModel.kt
│   │   │   └── components/
│   │   │       ├── CreditBadge.kt
│   │   │       └── RiskIndicator.kt
│   │   ├── data/
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   └── dto/
│   │   │   │   │       ├── auth/
│   │   │   │   │       ├── billing/
│   │   │   │   │       ├── documents/
│   │   │   │   │       └── chat/
│   │   │   │   └── interceptor/
│   │   │   │       └── JwtAuthInterceptor.kt
│   │   │   ├── local/
│   │   │   │   ├── database/
│   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   ├── entities/
│   │   │   │   │   ├── dao/
│   │   │   │   │   └── converters/
│   │   │   │   ├── preferences/
│   │   │   │   │   └── DataStoreManager.kt
│   │   │   │   └── ocr/
│   │   │   │       └── OcrEngine.kt
│   │   │   └── repository/
│   │   │       ├── AuthRepository.kt
│   │   │       ├── DocumentRepository.kt
│   │   │       ├── ChatRepository.kt
│   │   │       └── BillingRepository.kt
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── User.kt
│   │   │   │   ├── Document.kt
│   │   │   │   ├── ChatMessage.kt
│   │   │   │   └── enums/
│   │   │   │       ├── RiskSeverity.kt
│   │   │   │       ├── ProcessingStatus.kt
│   │   │   │       └── MessageSender.kt
│   │   │   └── usecase/
│   │   │       ├── auth/
│   │   │       ├── documents/
│   │   │       ├── chat/
│   │   │       └── billing/
│   │   ├── di/
│   │   │   ├── AppModule.kt
│   │   │   ├── NetworkModule.kt
│   │   │   ├── DatabaseModule.kt
│   │   │   └── RepositoryModule.kt
│   │   └── navigation/
│   │       ├── Screen.kt
│   │       └── NavGraph.kt
│   └── src/main/res/
│       ├── drawable/
│       ├── mipmap-*/
│       ├── values/
│       └── xml/
├── build.gradle.kts
└── gradle/
```

## Implementation Phases

### **Phase 1: Foundation & Dependencies**
- Update `gradle/libs.versions.toml` with all required dependencies
- Add dependencies to `app/build.gradle.kts`:
  - Firebase Auth & Storage
  - Retrofit, Moshi, OkHttp
  - Room, SQLCipher
  - Hilt
  - CameraX, ML Kit
  - WorkManager
  - Navigation Compose
  - DataStore
  - Biometric
- Setup Hilt application class
- Configure Moshi with enum adapters

### **Phase 2: Data Layer**
- Create domain models (enums, data classes)
- Create API DTOs from OpenAPI spec
- Create ApiService interface with all endpoints
- Create JwtAuthInterceptor
- Setup Room database with SQLCipher
- Create entities and DAOs
- Setup DataStore for preferences
- Create repositories

### **Phase 3: Dependency Injection**
- Create NetworkModule (Retrofit, Moshi, OkHttp)
- Create DatabaseModule (Room, SQLCipher)
- Create RepositoryModule
- Create AppModule (Firebase, DataStore)
- Setup Hilt in Application class

### **Phase 4: Authentication Flow**
- Create AuthViewModel
- Create LoginScreen with Firebase Auth
- Create OnboardingScreen (3 slides)
- Implement Firebase token management
- Create auth navigation
- Sync user profile on startup

### **Phase 5: Camera & OCR**
- Create CameraScreen with CameraX
- Create CameraViewModel
- Integrate ML Kit Text Recognition
- Implement image processing
- Create page accumulation logic
- Add edge detection overlay

### **Phase 6: Document Vault**
- Create DocumentListScreen
- Create DocumentListViewModel
- Create DocumentDetailScreen
- Create DocumentDetailViewModel
- Implement biometric lock
- Add FTS search
- Display risk flags and deadlines

### **Phase 7: Chat & Q&A**
- Create ChatScreen
- Create ChatViewModel
- Implement message list with LazyColumn
- Add typing indicator
- Integrate document Q&A API

### **Phase 8: Profile & Billing**
- Create ProfileScreen
- Create ProfileViewModel
- Display usage stats
- Show credit balance
- Add settings

### **Phase 9: Navigation**
- Create Screen sealed class
- Setup navigation graph
- Add bottom navigation
- Implement deep linking
- Create splash screen

### **Phase 10: Background Tasks**
- Setup WorkManager for document polling
- Create notification channels
- Implement AlarmManager for deadlines
- Add broadcast receivers

### **Phase 11: Polish & Testing**
- Add error handling
- Implement loading states
- Add unit tests
- Add UI tests
- Performance optimization
- Security audit

## Key Technical Decisions

**Architecture:** Single module with package-based separation (data, domain, ui, di, navigation)

**State Management:** ViewModel + StateFlow + MVI pattern

**DI:** Hilt for dependency injection

**Network:** Retrofit with Moshi, OkHttp with JWT interceptor

**Database:** Room with SQLCipher, FTS4 for search

**Async:** Coroutines throughout, Flow for reactive streams

**Background:** WorkManager for polling, AlarmManager for deadlines

**Security:** BiometricPrompt, Keystore, SQLCipher

**Navigation:** Compose Navigation with type-safe routes

**UI:** Jetpack Compose with Material 3
