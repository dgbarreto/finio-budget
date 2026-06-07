package dev.finio.budget.data.remote

import dev.finio.budget.data.dto.BudgetDto
import dev.finio.budget.data.dto.CreateBudgetRequestDto
import dev.finio.budget.data.dto.UpdateBudgetRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class BudgetRemoteDataSource(
    private val client: HttpClient,
    private val baseUrl: String
){
    suspend fun getBudgets(token: String): List<BudgetDto> =
        client.get("$baseUrl/budgets"){
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()

    suspend fun createBudget(
        token: String,
        request: CreateBudgetRequestDto
    ): BudgetDto =
        client.post("$baseUrl/budgets"){
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun updateBudget(
        token: String,
        id: String,
        request: UpdateBudgetRequestDto
    ): BudgetDto =
        client.put("$baseUrl/budgets/$id"){
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun deleteBudget(
        token: String,
        id: String
    ) = client.delete("$baseUrl/budgets/$id"){
        header(HttpHeaders.Authorization, "Bearer $token")
    }
}