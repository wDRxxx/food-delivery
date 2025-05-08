package com.example.fooddelivery.items

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.edit
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Cart
import com.example.fooddelivery.models.CartItem
import com.example.fooddelivery.models.FastFood
import com.google.gson.Gson

class FoodItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var base: FrameLayout
    private var title: TextView
    private var image: ImageView
    private var restaurant: TextView
    private var price: TextView
    private var addBtn: TextView

    private var clickListener: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, this, true)

        base = findViewById(R.id.base)
        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
        restaurant = findViewById(R.id.resturant)
        price = findViewById(R.id.price)
        addBtn = findViewById(R.id.addBtn)

        base.setOnClickListener {
            clickListener?.invoke()
        }

        // TODO: add to cart functionality
    }

    fun bind(fastFood: FastFood, onClick: () -> Unit) {
        title.text = fastFood.title
        image.load(fastFood.image.toString())
        restaurant.text = fastFood.restaurant?.title
        price.text = fastFood.price.toString() + "$"

        clickListener = onClick
        addBtn.setOnClickListener {
            addToCart(fastFood)
        }
    }

    fun addToCart(fastFood: FastFood) {
        val cartItem = CartItem(
            count = 1,
            food = fastFood,
            totalPrice = fastFood.price!!
        )

        val sharedPreferences = context.getSharedPreferences("cart", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cart", gson.toJson(Cart(items = listOf())))
        val cart: Cart = gson.fromJson(json, Cart::class.java)

        val existingItem = cart.items.find { it.food?.id == cartItem.food?.id }

        val updatedItems = if (existingItem != null) {
            cart.items.map {
                if (it.food?.id == cartItem.food?.id) {
                    it.copy(
                        count = it.count + cartItem.count,
                        totalPrice = it.totalPrice + cartItem.totalPrice
                    )
                } else it
            }
        } else {
            cart.items + cartItem
        }

        cart.items = updatedItems

        sharedPreferences.edit {
            putString("cart", gson.toJson(cart))
        }
    }
}