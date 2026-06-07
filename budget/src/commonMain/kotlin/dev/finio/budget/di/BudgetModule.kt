package dev.finio.budget.di

import dev.finio.budget.data.remote.BudgetRemoteDataSource
import dev.finio.budget.presentation.BudgetViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

fun budgetModule(
    baseUrl: String,
    tokenProvider: () -> String?
): Module = module {
   single{
       HttpClient{
           install(ContentNegotiation){
               json(Json { ignoreUnknownKeys = true })
           }
           install(Logging){
               level = LogLevel.BODY
               logger = object : Logger{
                   override fun log(message: String){
                       println("[Finio Budget] $message")
                   }
               }
           }
       }
   }

    single{
        BudgetRemoteDataSource(client = get(), baseUrl = baseUrl)
    }

    factory{
        BudgetViewModel(repository = get())
    }
}