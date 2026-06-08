# finio-budget

KMP budget module for the Finio platform. Encapsulates all budget logic — API calls, spending progress calculation, and budget state — published to Maven for consumption by `finio-app`.

## Stack

- **Language**: Kotlin Multiplatform
- **HTTP**: Ktor Client 3.1.3
- **Serialization**: kotlinx.serialization 1.8.1
- **Coroutines**: kotlinx.coroutines 1.10.2
- **DI**: Koin 4.0.0
- **Publication**: GitHub Packages (Maven)
- **CI/CD**: Bitrise

## Targets

| Target | Status |
|--------|--------|
| Android | ✅ |
| iOS Arm64 | ✅ |
| iOS Simulator Arm64 | ✅ |

## Module structure

```
budget/src/
  commonMain/
    kotlin/dev/finio/budget/
      data/
        dto/
          BudgetDtos.kt              ← API request and response DTOs
        mapper/
          BudgetMapper.kt            ← DTO → domain model mappers
        remote/
          BudgetRemoteDataSource.kt  ← Ktor API calls
        repository/
          BudgetRepositoryImpl.kt    ← Repository implementation
      di/
        BudgetModule.kt              ← Koin module definition
      domain/
        model/
          Budget.kt                  ← Budget domain model
          BudgetPeriod.kt            ← enum: MONTHLY | WEEKLY
        repository/
          BudgetRepository.kt        ← Repository interface
      presentation/
        BudgetViewModel.kt           ← ViewModel with StateFlow
```

## API endpoints

All endpoints are served by `finio-api` deployed on Railway.

| Method | Route | Description | Auth |
|--------|-------|-------------|------|
| GET | `/budgets` | List budgets with spending progress | ✓ |
| POST | `/budgets` | Create budget | ✓ |
| PUT | `/budgets/:id` | Update budget | ✓ |
| DELETE | `/budgets/:id` | Delete budget | ✓ |

## ViewModel state

```kotlin
sealed class BudgetState {
    object Loading : BudgetState()
    data class Success(val budgets: List<Budget>) : BudgetState()
    data class Error(val message: String) : BudgetState()
}
```

## Budget model

```kotlin
data class Budget(
    val id: String,
    val category: String,
    val limit: Double,
    val period: BudgetPeriod,   // MONTHLY | WEEKLY
    val startDate: String,
    val endDate: String,
    val spent: Double,          // calculated by the API
    val remaining: Double,      // calculated by the API
    val percentage: Int,        // calculated by the API
    val exceeded: Boolean       // calculated by the API
)
```

## DI usage

Initialize from your app shell (not from this module):

```kotlin
// Android — inside Application.onCreate()
initKoin {
    modules(budgetModule(
        baseUrl = "https://your-api.railway.app",
        tokenProvider = { tokenStorage.getToken() }
    ))
}

// iOS — inside AppDelegate or @main
// use KMP bridge to call initKoin
```

## Maven artifacts

Published to GitHub Packages under `dev.finio` group:

| Artifact | Description |
|----------|-------------|
| `budget-android` | Android AAR |
| `budget-iosarm64` | iOS Arm64 klib |
| `budget-iossimulatorarm64` | iOS Simulator Arm64 klib |
| `budget-kmp` | KMP metadata |

## CI/CD

| Trigger | Workflow | Action |
|---------|----------|--------|
| Push to `main` | `ci` | Compiles Android AAR + iOS Arm64 |
| Any tag (e.g. `0.1.0`) | `release` | Publishes all artifacts to GitHub Packages |

## Build

```bash
# Compile all targets
./gradlew :budget:assemble

# Publish to local Maven (~/.m2)
./gradlew :budget:publishToMavenLocal

# Publish to GitHub Packages (requires GITHUB_ACTOR and GITHUB_TOKEN)
./gradlew :budget:publish
```

## Key versions

```toml
kotlin = "2.3.21"
agp = "9.0.1"
ktor = "3.1.3"
koin = "4.0.0"
kotlinx-coroutines = "1.10.2"
kotlinx-serialization = "1.8.1"
```