

# ScanLegal Implementation Plan

## Module Structure & API Mapping

```
scanlegal/
├── app/                          # Main application module
├── core/
│   ├── network/                  # Retrofit + API definitions
│   │   ├── dto/                  # All API request/response models
│   │   │   ├── auth/
│   │   │   │   ├── ProfileCreate.kt
│   │   │   │   ├── ProfileResponse.kt
│   │   │   │   ├── ProfileWithPreferences.kt
│   │   │   │   └── UserPreferencesUpdate.kt
│   │   │   ├── billing/
│   │   │   │   └── UsageResponse.kt
│   │   │   ├── documents/
│   │   │   │   ├── DocumentAnalysisRequest.kt
│   │   │   │   ├── DocumentResponse.kt
│   │   │   │   ├── DocumentDetailResponse.kt
│   │   │   │   ├── DocumentStatusResponse.kt
│   │   │   │   ├── DocumentQARequest.kt
│   │   │   │   ├── DocumentQAResponse.kt
│   │   │   │   └── DocumentCompareRequest.kt
│   │   │   └── chat/
│   │   │       ├── ChatSessionBase.kt
│   │   │       ├── ChatSessionResponse.kt
│   │   │       ├── ChatSessionWithMessages.kt
│   │   │       ├── ChatMessageCreate.kt
│   │   │       └── ChatMessageResponse.kt
│   │   ├── api/
│   │   │   ├── ApiService.kt     # Retrofit interface with all endpoints
│   │   │   └── interceptor/
│   │   │       └── JwtAuthInterceptor.kt
│   │   └── util/
│   │       └── ResultWrapper.kt  # Network result handling
│   ├── database/                 # Room + SQLCipher
│   │   ├── AppDatabase.kt
│   │   ├── entities/
│   │   │   ├── DocumentEntity.kt
│   │   │   ├── AnalysisFlagEntity.kt
│   │   │   ├── DeadlineEntity.kt
│   │   │   └── ChatMessageEntity.kt
│   │   ├── dao/
│   │   │   ├── DocumentDao.kt
│   │   │   └── ChatDao.kt
│   │   └── converter/
│   │       └── Converters.kt     # Date, enum converters
│   └── common/                   # Shared models, utils
│       ├── model/
│       │   ├── RiskSeverity.kt
│       │   ├── ProcessingStatus.kt
│       │   └── MessageSender.kt
│       └── util/
│           ├── DateUtils.kt
│           └── EncryptionUtils.kt
└── features/
    ├── auth/
    │   ├── data/
    │   │   ├── repository/
    │   │   │   └── AuthRepositoryImpl.kt
    │   │   └── remote/
    │   │       └── FirebaseAuthDataSource.kt
    │   ├── domain/
    │   │   ├── model/
    │   │   │   └── User.kt
    │   │   ├── repository/
    │   │   │   └── AuthRepository.kt
    │   │   └── usecase/
    │   │       ├── RegisterUserUseCase.kt
    │   │       ├── SyncUserUseCase.kt
    │   │       ├── GetCurrentUserUseCase.kt
    │   │       ├── UpdatePreferencesUseCase.kt
    │   │       └── DeleteAccountUseCase.kt
    │   └── presentation/
    │       ├── login/
    │       │   ├── LoginScreen.kt
    │       │   └── LoginViewModel.kt
    │       ├── onboarding/
    │       │   ├── OnboardingScreen.kt
    │       │   └── OnboardingViewModel.kt
    │       └── navigation/
    │           └── AuthNavigation.kt
    ├── scan/
    │   ├── data/
    │   │   ├── repository/
    │   │   │   └── ScanRepositoryImpl.kt
    │   │   └── local/
    │   │       ├── OcrEngine.kt     # ML Kit wrapper
    │   │       └── ImageProcessor.kt
    │   ├── domain/
    │   │   ├── model/
    │   │   │   └── ScannedPage.kt
    │   │   ├── repository/
    │   │   │   └── ScanRepository.kt
    │   │   └── usecase/
    │   │       ├── CapturePageUseCase.kt
    │   │       ├── ProcessOcrUseCase.kt
    │   │       └── SubmitDocumentUseCase.kt
    │   └── presentation/
    │       ├── camera/
    │       │   ├── CameraScreen.kt
    │       │   └── CameraViewModel.kt
    │       ├── preview/
    │       │   ├── DocumentPreviewScreen.kt
    │       │   └── DocumentPreviewViewModel.kt
    │       └── navigation/
    │           └── ScanNavigation.kt
    ├── vault/
    │   ├── data/
    │   │   ├── repository/
    │   │   │   └── DocumentRepositoryImpl.kt
    │   │   └── local/
    │   │       └── BiometricAuth.kt
    │   ├── domain/
    │   │   ├── model/
    │   │   │   └── Document.kt
    │   │   ├── repository/
    │   │   │   └── DocumentRepository.kt
    │   │   └── usecase/
    │   │       ├── GetDocumentsUseCase.kt
    │   │       ├── GetDocumentDetailUseCase.kt
    │   │       ├── UpdateDocumentUseCase.kt
    │   │       ├── DeleteDocumentUseCase.kt
    │   │       ├── SearchDocumentsUseCase.kt
    │   │       └── CompareDocumentsUseCase.kt
    │   └── presentation/
    │       ├── list/
    │       │   ├── DocumentListScreen.kt
    │       │   └── DocumentListViewModel.kt
    │       ├── detail/
    │       │   ├── DocumentDetailScreen.kt
    │       │   └── DocumentDetailViewModel.kt
    │       └── navigation/
    │           └── VaultNavigation.kt
    ├── chat/
    │   ├── data/
    │   │   └── repository/
    │   │       └── ChatRepositoryImpl.kt
    │   ├── domain/
    │   │   ├── model/
    │   │   │   └── ChatMessage.kt
    │   │   ├── repository/
    │   │   │   └── ChatRepository.kt
    │   │   └── usecase/
    │   │       ├── CreateChatSessionUseCase.kt
    │   │       ├── GetChatSessionUseCase.kt
    │   │       ├── SendMessageUseCase.kt
    │   │       └── AskDocumentQuestionUseCase.kt
    │   └── presentation/
    │       ├── chat/
    │       │   ├── ChatScreen.kt
    │       │   └── ChatViewModel.kt
    │       └── navigation/
    │           └── ChatNavigation.kt
    └── analytics/
        ├── data/
        │   └── repository/
        │       └── BillingRepositoryImpl.kt
        ├── domain/
        │   ├── model/
        │   │   └── UsageStats.kt
        │   ├── repository/
        │   │   └── BillingRepository.kt
        │   └── usecase/
        │       └── GetUsageUseCase.kt
        └── presentation/
            ├── profile/
            │   ├── ProfileScreen.kt
            │   └── ProfileViewModel.kt
            └── navigation/
                └── AnalyticsNavigation.kt
```

## Implementation Phases

### **Phase 1: Foundation (Core Modules)**
- Setup multi-module Gradle structure
- Configure `:core:network` with Retrofit, Moshi/Gson, OkHttp
- Configure `:core:database` with Room, SQLCipher
- Configure `:core:common` with shared models
- Add Hilt DI setup across modules
- Define all DTOs from OpenAPI spec

### **Phase 2: Authentication Flow**
- Implement `:features:auth` module
- Firebase Auth integration (Google/Apple sign-in)
- JWT interceptor for API calls
- Login screen with Firebase
- Onboarding screens (3 slides)
- Sync user profile on app startup
- Map API endpoints: `/api/v1/auth/register`, `/api/v1/auth/sync`, `/api/v1/auth/me`, `/api/v1/auth/profile`, `/api/v1/auth/account`

### **Phase 3: Camera & OCR**
- Implement `:features:scan` module
- CameraX integration with PreviewView
- Edge detection overlay
- ML Kit Text Recognition SDK
- Image processing (crop, contrast filter)
- Page accumulation in ViewModel
- Document preview screen
- Map to API: `/api/v1/documents/analyse` (raw_text mode)

### **Phase 4: Encrypted Vault**
- Implement `:features:vault` module
- Room database with SQLCipher
- Android Keystore for encryption keys
- BiometricPrompt for vault access
- Document list with FTS search
- Document detail with risk flags
- Map API endpoints: `/api/v1/documents` (list, get, update, delete), `/api/v1/documents/{id}/status`

### **Phase 5: Background Sync**
- Implement WorkManager integration
- Document status polling worker
- Notification on completion
- Handle app lifecycle events
- Firebase Storage upload wrapper

### **Phase 6: Chat & Q&A**
- Implement `:features:chat` module
- Chat session management
- LazyColumn with message list
- Streaming response handling
- Context-isolated Q&A
- Map API endpoints: `/api/v1/chat` (list, create, get), `/api/v1/chat/{id}/messages`, `/api/v1/documents/{id}/qa`

### **Phase 7: Billing & Analytics**
- Implement `:features:analytics` module
- Usage stats display
- Credit balance tracking
- RevenueCat integration (optional)
- Map API: `/api/v1/billing/usage`

### **Phase 8: Deadline Tracking**
- AlarmManager integration
- Deadline notification system
- Deadline confirmation UI
- Broadcast receiver for triggers

### **Phase 9: App Integration**
- Setup navigation graph
- Bottom navigation implementation
- Deep linking support
- Splash screen
- Error handling globally

### **Phase 10: Polish & Testing**
- Unit tests for use cases
- Instrumented tests for repositories
- UI tests with Compose Testing
- Performance optimization
- Security audit

## Key Technical Decisions

**Dependency Injection:** Hilt across all modules with `@Module` and `@InstallIn` per module scope

**State Management:** ViewModel + StateFlow + MVI pattern (UiEvent → UiState)

**Network:** Retrofit with Moshi for JSON, OkHttp with JWT interceptor

**Database:** Room with SQLCipher, FTS4 for search, TypeConverters for enums/dates

**Async:** Coroutines throughout, Flow for reactive streams

**Background:** WorkManager for polling, AlarmManager for exact-time deadlines

**Security:** BiometricPrompt for vault, Keystore for keys, SQLCipher for encryption

**Navigation:** Compose Navigation with type-safe routes, nested graphs for features

Ready to start with Phase 1?