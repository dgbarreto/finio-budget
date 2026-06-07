package dev.finio.budget.domain.repository

import dev.finio.budget.domain.model.Budget
import dev.finio.budget.domain.model.BudgetPeriod

interface BudgetRepository{
    suspend fun getBudgets(): List<Budget>
    suspend fun createBudget(
        category: String,
        limit: Double,
        period: BudgetPeriod,
        startDate: String,
        endDate: String
    ): Budget
    suspend fun updateBudget(
        id: String,
        category: String? = null,
        limit: Double? = null,
        period: BudgetPeriod? = null,
        startDate: String? = null,
        endDate: String? = null
    ): Budget
    suspend fun deleteBudget(id: String)
}