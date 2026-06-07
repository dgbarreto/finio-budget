package dev.finio.budget.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BudgetDto(
    val _id: String,
    val category: String,
    val limit: Double,
    val period: String,
    val startDate: String,
    val endDate: String,
    val spent: Double,
    val remaining: Double,
    val percentage: Int,
    val exceeded: Boolean
)

@Serializable
data class CreateBudgetRequestDto(
    val category: String,
    val limit: Double,
    val period: String,
    val startDate: String,
    val endDate: String
)

@Serializable
data class UpdateBudgetRequestDto(
    val category: String? = null,
    val limit: Double? = null,
    val period: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
)