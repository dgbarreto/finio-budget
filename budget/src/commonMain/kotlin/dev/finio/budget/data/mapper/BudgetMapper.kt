package dev.finio.budget.data.mapper

import dev.finio.budget.data.dto.BudgetDto
import dev.finio.budget.domain.model.Budget
import dev.finio.budget.domain.model.BudgetPeriod

fun BudgetDto.toDomain(): Budget = Budget(
    id = _id,
    category = category,
    limit = limit,
    period = BudgetPeriod.valueOf(period.uppercase()),
    startDate = startDate,
    endDate = endDate,
    spent = spent,
    remaining = remaining,
    percentage = percentage,
    exceeded = exceeded
)