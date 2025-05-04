package com.example.fooddelivery.models

import java.io.Serializable

data class Restaurant(
    val id: Int,
    val image: String?,
    val title: String,
    val categories: List<Category>?,
    val rating: Float?,
    val delivery: String?,
    val deliveryTime: String?,
    val description: String?
) : Serializable

val restaurants: List<Restaurant> = listOf(
    Restaurant(
        id = 1,
        image = "https://avatars.mds.yandex.net/get-altay/223006/2a0000015b17a9dcb31ff3c8b77997f8d521/L_height",
        title = "Pizza house",
        categories = categories.subList(0, 3),
        rating = 4.7f,
        delivery = "free",
        deliveryTime = "20 min",
        description = "Best pizza is here!"
    ),
    Restaurant(
        id = 4,
        image = "https://i.redd.it/xx0ue22echp81.jpg",
        title = "Cafenio Coffee Club",
        categories = categories.subList(0, 3),
        rating = 4.1f,
        delivery = "1$",
        deliveryTime = "10 min",
        description = "Maecenas sed diam eget risus varius blandit sit amet non magna. Integer posuere erat a ante venenatis dapibus posuere velit aliquet."
    ),
    Restaurant(
        id = 5,
        image = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/02/2e/87/0d/getlstd-property-photo.jpg?w=900&h=500&s=1",
        title = "Rose Garden Restaurant",
        categories = categories.subList(0, 2),
        rating = 3.8f,
        delivery = "free",
        deliveryTime = "20 min",
        description = "Maecenas sed diam eget risus varius blandit sit amet non magna. Integer posuere erat a ante venenatis dapibus posuere velit aliquet."
    ),
)
