package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Restaurant

class RestaurantSearchItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var rating: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_restaurant_search, this, true)

        title = findViewById(R.id.title)
        rating = findViewById(R.id.rating)
    }

    fun bind(restaurant: Restaurant) {
        title.text = restaurant.title
        rating.text = restaurant.rating.toString()
    }
}