package com.example.fooddelivery.models

import java.io.Serializable

data class FastFood(
    val id: Int,
    val title: String,
    val restaurant: Restaurant?,
    val price: Float?,
) : Serializable

val fastfoods: List<FastFood> = listOf(
    FastFood(
        id = 1,
        title = "European Pizza",
        restaurant = restaurants[0],
        price = 12f
    ),
    FastFood(
        id = 2,
        title = "Buffalo Pizza",
        restaurant = restaurants[1],
        price = 15.9f
    ),
    FastFood(
        id = 2,
        title = "Big Hot Dog",
        restaurant = restaurants[2],
        price = 4f
    ),
)