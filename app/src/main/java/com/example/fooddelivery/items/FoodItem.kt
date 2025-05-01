package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.FastFood

class FoodItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var restaurant: TextView
    private var price: TextView
    private var addBtn: TextView

    private var clickListener: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, this, true)

        title = findViewById(R.id.title)
        restaurant = findViewById(R.id.resturant)
        price = findViewById(R.id.price)
        addBtn = findViewById(R.id.addBtn)

        view.setOnClickListener {
            clickListener?.invoke()
        }

        // TODO: add to cart functionality
    }

    fun bind(fastFood: FastFood, onClick: () -> Unit) {
        title.text = fastFood.title
        restaurant.text = fastFood.restaurant?.title
        price.text = fastFood.price.toString() + "$"

        clickListener = onClick
    }
}