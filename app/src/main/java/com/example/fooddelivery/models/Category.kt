package com.example.fooddelivery.models

import java.io.Serializable

data class Category(
    val id: Int,
    val image: String?,
    val title: String
) : Serializable

val categories: List<Category> = listOf(
    Category(
        id = 1,
        image = "https://www.cobsbread.com/wp-content/uploads/2022/09/Pepperoni-pizza-850x630-1-585x400-1.jpg",
        title = "Pizza"
    ),
    Category(
        id = 2,
        image = "https://www.belbrandsfoodservice.com/wp-content/uploads/2018/05/recipe-desktop-merkts-cheesy-hot-dawg.jpg",
        title = "Hot Dog"
    ),
    Category(
        id = 3,
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpSkKP8LgqK1IPs87-PyJcveeRF0Wet-xgyw&s",
        title = "Burger"
    ),
    Category(
        id = 4,
        image = "https://e2.edimdoma.ru/data/posts/0002/2702/22702-ed4_wide.jpg?1745910737",
        title = "Sandwich"
    )
)