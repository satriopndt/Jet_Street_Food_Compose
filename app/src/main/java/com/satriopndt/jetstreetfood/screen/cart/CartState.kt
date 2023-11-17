package com.satriopndt.jetstreetfood.screen.cart

import com.satriopndt.jetstreetfood.model.OrderFood

data class CartState(
    val orderFood: List<OrderFood>,
    val totalPrice: Int
)
