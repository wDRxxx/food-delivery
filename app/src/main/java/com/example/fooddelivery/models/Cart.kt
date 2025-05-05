package com.example.fooddelivery.models

import java.io.Serializable

data class CartItem(
    var count: Int,
    var food: FastFood?,
    var totalPrice: Float
) : Serializable

data class Cart(
    var items: List<CartItem>
) : Serializable

data class OrderItem(
    var id: Int,
    var date: String,
    var items: List<CartItem>
) : Serializable

data class Orders(
    var items: List<OrderItem>
) : Serializable

