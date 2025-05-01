package com.example.fooddelivery.models

import java.io.Serializable

data class FastFood(
    val id: Int,
    val title: String,
    val restaurant: Restaurant?,
    val price: Float?,
    val category: Category,
) : Serializable

val fastfoods: List<FastFood> = listOf(
    FastFood(
        id = 1,
        title = "European Pizza",
        restaurant = restaurants[0],
        price = 12f,
        category = categories[0]
    ),
    FastFood(
        id = 2,
        title = "Buffalo Pizza",
        restaurant = restaurants[1],
        price = 15.9f,
        category = categories[0]
    ),
    FastFood(
        id = 2,
        title = "Big Hot Dog",
        restaurant = restaurants[2],
        price = 4f,
        category = categories[1]
    ),
    FastFood(
        id = 3,
        title = "Big Mac",
        restaurant = restaurants[0],
        price = 5f,
        category = categories[2]
    ),
    FastFood(
        id = 4,
        title = "American Pizza",
        restaurant = restaurants[0],
        price = 13f,
        category = categories[0]
    ),
    FastFood(
        id = 5,
        title = "Meat Master",
        restaurant = restaurants[0],
        price = 8f,
        category = categories[2]
    ),
)