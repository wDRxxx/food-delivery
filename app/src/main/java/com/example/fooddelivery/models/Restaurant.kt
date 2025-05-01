package com.example.fooddelivery.models

data class Restaurant(
    val id: Int,
    val title: String,
    val categories: List<Category>?,
    val rating: Float?,
    val delivery: String?,
    val deliveryTime: String?,
    val description: String?
)

val restaurants: List<Restaurant> = listOf(
    Restaurant(
        id = 1,
        title = "Pizza house",
        categories = categories.subList(0, 2),
        rating = 4.7f,
        delivery = "free",
        deliveryTime = "20 min",
        description = "Great Rose Garden Restaurant"
    ),
    Restaurant(
        id = 4,
        title = "Cafenio Coffee Club",
        categories = categories.subList(0, 3),
        rating = 4.1f,
        delivery = "1$",
        deliveryTime = "10 min",
        description = "Maecenas sed diam eget risus varius blandit sit amet non magna. Integer posuere erat a ante venenatis dapibus posuere velit aliquet."
    ),
    Restaurant(
        id = 5,
        title = "Rose Garden Restaurant",
        categories = categories.subList(0, 2),
        rating = 3.8f,
        delivery = "free",
        deliveryTime = "20 min",
        description = "Maecenas sed diam eget risus varius blandit sit amet non magna. Integer posuere erat a ante venenatis dapibus posuere velit aliquet."
    ),
)
