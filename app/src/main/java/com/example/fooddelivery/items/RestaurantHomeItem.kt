package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Restaurant

class RestaurantHomeItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var image: ImageView
    private var categories: TextView
    private var rating: TextView
    private var delivery: TextView
    private var deliveryTime: TextView

    private var clickListener: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_home, this, true)

        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
        categories = findViewById(R.id.categories)
        rating = findViewById(R.id.rating)
        delivery = findViewById(R.id.delivery)
        deliveryTime = findViewById(R.id.deliveryTime)

        view.setOnClickListener {
            clickListener?.invoke()
        }
    }

    fun bind(restaurant: Restaurant, onClick: () -> Unit) {
        title.text = restaurant.title
        categories.text = restaurant.categories?.joinToString(separator = " - ") { it.title }
        rating.text = restaurant.rating.toString()
        delivery.text = restaurant.delivery
        deliveryTime.text = restaurant.deliveryTime

//        Glide.with(context).load(restaurant.image.toString()).into(image)

        image.load(restaurant.image.toString())
        clickListener = onClick
    }
}