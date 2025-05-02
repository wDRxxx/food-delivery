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
