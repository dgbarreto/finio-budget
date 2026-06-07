package dev.finio.budget.presentation

import dev.finio.budget.domain.model.Budget
import dev.finio.budget.domain.model.BudgetPeriod
import dev.finio.budget.domain.repository.BudgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class BudgetState{
    object Loading: BudgetState()
    data class Success(val budgets: List<Budget>): BudgetState()
    data class Error(val message: String): BudgetState()
}

class BudgetViewModel (
    private val repository: BudgetRepository
){
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow<BudgetState>(BudgetState.Loading)
    val state: StateFlow<BudgetState> = _state.asStateFlow()

    init {
        load()
    }

    fun load(){
        viewModelScope.launch {
            try {
                _state.value = BudgetState.Loading
                _state.value = BudgetState.Success(repository.getBudgets())
            } catch (e: Exception){
                _state.value = BudgetState.Error(e.message ?: "Failed to load budgets")
            }
        }
    }

    fun createBudget(
        category: String,
        limit: Double,
        period: BudgetPeriod,
        startDate: String,
        endDate: String
    ){
        viewModelScope.launch {
            try {
                repository.createBudget(category, limit, period, startDate, endDate)
                load()
            } catch (e: Exception){
                _state.value = BudgetState.Error(e.message ?: "Failed to create budget")
            }
        }
    }

    fun deleteBudget(id: String){
        viewModelScope.launch {
            try {
                repository.deleteBudget(id)
                load()
            } catch (e: Exception){
                _state.value = BudgetState.Error(e.message ?: "Failed to delete budget")
            }
        }
    }
}