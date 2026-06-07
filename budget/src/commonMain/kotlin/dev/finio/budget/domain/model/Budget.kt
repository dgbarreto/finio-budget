package dev.finio.budget.domain.model

data class Budget(
    val id: String,
    val category: String,
    val limit: Double,
    val period: BudgetPeriod,
    val startDate: String,
    val endDate: String,
    val spent: Double,
    val remaining: Double,
    val percentage: Int,
    val exceeded: Boolean
)