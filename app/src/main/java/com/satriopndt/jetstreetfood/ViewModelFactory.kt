package com.satriopndt.jetstreetfood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.satriopndt.jetstreetfood.repository.StreetFoodRepository
import com.satriopndt.jetstreetfood.screen.cart.CartViewModel
import com.satriopndt.jetstreetfood.screen.detail.DetailViewModel
import com.satriopndt.jetstreetfood.screen.home.HomeViewModel

class ViewModelFactory(private val repository: StreetFoodRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}