package com.satriopndt.jetstreetfood.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satriopndt.jetstreetfood.repository.StreetFoodRepository
import com.satriopndt.jetstreetfood.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: StreetFoodRepository
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrder(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddOrderFood()
                .collect{orderFood ->
                    val totaPrice = orderFood.sumOf { it.streetFood.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderFood, totaPrice))
                }
        }
    }

    fun updateOrder(foodId: Long, count: Int){
        viewModelScope.launch {
            repository.updateOrderFood(foodId, count)
                .collect { isUpdate ->
                    if (isUpdate) {
                        getAddedOrder()
                    }
                }
        }
    }
}