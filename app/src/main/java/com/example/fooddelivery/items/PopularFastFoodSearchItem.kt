package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.FastFood

class PopularFastFoodSearchItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var image: ImageView
    private var restaurant: TextView

    private var clickListener: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_fastfood_search, this, true)

        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
        restaurant = findViewById(R.id.resturant)

        view.setOnClickListener {
            clickListener?.invoke()
        }
    }

    fun bind(fastFood: FastFood, onClick: () -> Unit) {
        title.text = fastFood.title
        image.load(fastFood.image.toString())
        restaurant.text = fastFood.restaurant?.title

        clickListener = onClick
    }
}