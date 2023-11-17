package com.satriopndt.jetstreetfood.repository

import com.satriopndt.jetstreetfood.model.OrderFood
import com.satriopndt.jetstreetfood.model.StreetFood
import com.satriopndt.jetstreetfood.model.StreetFoodData
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.concurrent.Flow

class StreetFoodRepository {

    private val orderFood = mutableListOf<OrderFood>()

    init {
        if (orderFood.isEmpty()) {
            StreetFoodData.dummyFood.forEach {
                orderFood.add(OrderFood(it,0))
            }
        }
    }

    fun searchFoodies(query: String): List<StreetFood>{
        return StreetFoodData.dummyFood.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getFoods(): List<StreetFood>{
        return StreetFoodData.dummyFood
    }


    fun getAllFood(): kotlinx.coroutines.flow.Flow<List<OrderFood>> {
        return flowOf(orderFood)
    }

    fun getOrderFoodById(stfoodId: Long): OrderFood {
        return orderFood.first {
            it.streetFood.id == stfoodId
        }
    }

    fun updateOrderFood(foodId: Long, newCountValue: Int): kotlinx.coroutines.flow.Flow<Boolean> {
        val index = orderFood.indexOfFirst { it.streetFood.id == foodId }
        val result = if (index >= 0) {
            val orderFoods = orderFood[index]
            orderFood[index] =
                orderFoods.copy(
                    streetFood = orderFoods.streetFood,
                    count = newCountValue
                )
            true
        }else{
            false
        }
        return flowOf(result)
    }

    fun getAddOrderFood(): kotlinx.coroutines.flow.Flow<List<OrderFood>>{
        return getAllFood()
            .map { orderStreetFoods ->
                orderStreetFoods.filter { orderStreetFood ->
                    orderStreetFood.count != 0
                }
            }
    }

    companion object{
        @Volatile
        private var instance: StreetFoodRepository? = null

        fun getInstance(): StreetFoodRepository =
            instance ?: synchronized(this){
            StreetFoodRepository().apply {
                instance = this
            }
        }
    }
}