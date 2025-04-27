package com.example.fooddelivery.models

data class Restaurant(
    val title: String,
    val categories: List<Category>,
    val rating: Float,
    val delivery: String,
    val deliveryTime: String
)
