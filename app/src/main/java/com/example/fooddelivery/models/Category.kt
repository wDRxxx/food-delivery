package com.example.fooddelivery.models

data class Category(
    val id: Int,
    val title: String
)

val categories: List<Category> = listOf(
    Category(
        id = 1,
        title = "Pizza"
    ),
    Category(
        id = 2,
        title = "Hot Dog"
    ),
    Category(
        id = 3,
        title = "Burger"
    ),
    Category(
        id = 4,
        title = "Sandwich"
    )
)