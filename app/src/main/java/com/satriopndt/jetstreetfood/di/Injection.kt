package com.satriopndt.jetstreetfood.di

import com.satriopndt.jetstreetfood.repository.StreetFoodRepository

object Injection {
    fun provideRepository(): StreetFoodRepository{
        return StreetFoodRepository.getInstance()
    }
}