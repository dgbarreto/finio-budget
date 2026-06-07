package dev.finio.budget.domain.repository

import dev.finio.budget.data.dto.CreateBudgetRequestDto
import dev.finio.budget.data.dto.UpdateBudgetRequestDto
import dev.finio.budget.data.mapper.toDomain
import dev.finio.budget.data.remote.BudgetRemoteDataSource
import dev.finio.budget.domain.model.Budget
import dev.finio.budget.domain.model.BudgetPeriod

class BudgetRepositoryImpl(
    private val remoteDataSource: BudgetRemoteDataSource,
    private val tokenPRovider: () -> String?
): BudgetRepository{
    override suspend fun getBudgets(): List<Budget> {
        val token = tokenPRovider() ?: error("Not authenticated")
        return remoteDataSource.getBudgets(token).map { it.toDomain() }
    }

    override suspend fun createBudget(
        category: String,
        limit: Double,
        period: BudgetPeriod,
        startDate: String,
        endDate: String
    ): Budget {
        val token = tokenPRovider() ?: error("Not authenticated")
        return remoteDataSource.createBudget(
            token = token,
            request = CreateBudgetRequestDto(
                category = category,
                limit = limit,
                period = period.name.lowercase(),
                startDate = startDate,
                endDate = endDate
            )
        ).toDomain()
    }

    override suspend fun updateBudget(
        id: String,
        category: String?,
        limit: Double?,
        period: BudgetPeriod?,
        startDate: String?,
        endDate: String?
    ): Budget {
        val token = tokenPRovider() ?: error("Not authenticated")
        return remoteDataSource.updateBudget(
            token = token,
            id = id,
            request = UpdateBudgetRequestDto(
                category = category,
                limit = limit,
                period = period?.name?.lowercase(),
                startDate = startDate,
                endDate = endDate
            )
        ).toDomain()
    }

    override suspend fun deleteBudget(id: String) {
        val token = tokenPRovider() ?: error("Not authenticated")
        remoteDataSource.deleteBudget(token = token, id = id)
    }

}