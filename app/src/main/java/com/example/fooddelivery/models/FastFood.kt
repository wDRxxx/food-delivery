package com.example.fooddelivery.models

import java.io.Serializable

data class FastFood(
    val id: Int,
    val title: String,
    val image: String?,
    val restaurant: Restaurant?,
    val price: Float?,
    val category: Category,
) : Serializable

val fastfoods: List<FastFood> = listOf(
    FastFood(
        id = 1,
        title = "European Pizza",
        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/960px-Eq_it-na_pizza-margherita_sep2005_sml.jpg",
        restaurant = restaurants[0],
        price = 12f,
        category = categories[0]
    ),
    FastFood(
        id = 2,
        title = "Buffalo Pizza",
        image = "https://eggs.ca/wp-content/uploads/2024/06/EFC-pizza-with-eggs-1280x720-1.jpg",
        restaurant = restaurants[1],
        price = 15.9f,
        category = categories[0]
    ),
    FastFood(
        id = 2,
        title = "Big Hot Dog",
        image = "https://maplefromcanada.ca/uploads/2023/07/recette-hot-dog-tempeh-erable-1200x900-1-600x450.jpg",
        restaurant = restaurants[2],
        price = 4f,
        category = categories[1]
    ),
    FastFood(
        id = 3,
        title = "Big Mac",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpSkKP8LgqK1IPs87-PyJcveeRF0Wet-xgyw&s",
        restaurant = restaurants[0],
        price = 5f,
        category = categories[2]
    ),
    FastFood(
        id = 4,
        title = "American Pizza",
        image = "https://www.allrecipes.com/thmb/aefJMDXKqs42oAP71dQuYf_-Qdc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/6776_Pizza-Dough_ddmfs_4x3_1724-fd91f26e0bd6400a9e89c6866336532b.jpg",
        restaurant = restaurants[0],
        price = 13f,
        category = categories[0]
    ),
    FastFood(
        id = 5,
        title = "Meat Master",
        image = "https://www.foodandwine.com/thmb/DI29Houjc_ccAtFKly0BbVsusHc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/crispy-comte-cheesburgers-FT-RECIPE0921-6166c6552b7148e8a8561f7765ddf20b.jpg",
        restaurant = restaurants[0],
        price = 8f,
        category = categories[2]
    ),
)