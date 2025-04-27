package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Restaurant

class RestaurantHomeItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var categories: TextView
    private var rating: TextView
    private var delivery: TextView
    private var deliveryTime: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_restaurant_home, this, true)

        title = findViewById(R.id.title)
        categories = findViewById(R.id.categories)
        rating = findViewById(R.id.rating)
        delivery = findViewById(R.id.delivery)
        deliveryTime = findViewById(R.id.deliveryTime)
    }

    fun bind(restaurant: Restaurant) {
        title.text = restaurant.title
        categories.text = restaurant.categories.joinToString(separator = " - ") { it.title }
        rating.text = restaurant.rating.toString()
        delivery.text = restaurant.delivery
        deliveryTime.text = restaurant.deliveryTime
    }
}