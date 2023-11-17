package com.satriopndt.jetstreetfood.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satriopndt.jetstreetfood.model.OrderFood
import com.satriopndt.jetstreetfood.model.StreetFood
import com.satriopndt.jetstreetfood.repository.StreetFoodRepository
import com.satriopndt.jetstreetfood.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: StreetFoodRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderFood>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderFood>>
        get() = _uiState

    fun getFoodById(foodId: Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFoodById(foodId))
        }
    }

    fun addFoodToCart(streetFood: StreetFood, count: Int){
        viewModelScope.launch {
            repository.updateOrderFood(streetFood.id, count)
        }
    }
}