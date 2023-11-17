package com.satriopndt.jetstreetfood.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satriopndt.jetstreetfood.model.OrderFood
import com.satriopndt.jetstreetfood.model.StreetFood
import com.satriopndt.jetstreetfood.repository.StreetFoodRepository
import com.satriopndt.jetstreetfood.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: StreetFoodRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderFood>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderFood>>>
        get() = _uiState

    private val _groupFood = MutableStateFlow(
        repository.getFoods()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    fun getAllFoodies(){
        viewModelScope.launch {
            repository.getAllFood()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ orderFoods ->
                    _uiState.value = UiState.Success(orderFoods)
                }
        }
    }

    val groupFoodies: StateFlow<Map<Char, List<StreetFood>>> get() = _groupFood

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String){
        _query.value = newQuery
        _groupFood.value = repository.searchFoodies(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}