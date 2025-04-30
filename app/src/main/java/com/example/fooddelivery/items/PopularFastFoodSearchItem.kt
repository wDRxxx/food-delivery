package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.FastFood

class PopularFastFoodSearchItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var restaurant: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_fastfood_search, this, true)

        title = findViewById(R.id.title)
        restaurant = findViewById(R.id.resturant)
    }

    fun bind(fastFood: FastFood) {
        title.text = fastFood.title
        restaurant.text = fastFood.restaurant
    }
}