package com.example.fooddelivery.items

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.edit
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Cart
import com.example.fooddelivery.models.CartItem
import com.google.gson.Gson

class CartItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var cartItem: CartItem

    private var title: TextView
    private var image: ImageView
    private var price: TextView

    private var countText: TextView
    private var minusBtn: TextView
    private var plusBtn: TextView

    private var deleteBtn: ImageView

    private var onPriceUpdated: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_food, this, true)

        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
        price = findViewById(R.id.price)
        countText = view.findViewById(R.id.count)
        minusBtn = view.findViewById(R.id.minusBtn)
        minusBtn.setOnClickListener {
            if (cartItem.count > 1) {
                cartItem.count--
                cartItem.totalPrice -= cartItem.food?.price!!

                countText.text = cartItem.count.toString()
                price.text = cartItem.totalPrice.toString() + "$"
                addToCart()
            }
        }
        plusBtn = view.findViewById(R.id.plusBtn)
        plusBtn.setOnClickListener {
            cartItem.count++
            cartItem.totalPrice += cartItem.food?.price!!

            countText.text = cartItem.count.toString()
            price.text = cartItem.totalPrice.toString() + "$"
            addToCart()
        }

        deleteBtn = view.findViewById(R.id.deleteBtn)
    }

    fun bind(
        cartItem: CartItem,
        onPriceUpdated: () -> Unit,
        onDelete: (CartItem) -> Unit
    ) {
        this.cartItem = cartItem
        this.onPriceUpdated = onPriceUpdated

        title.text = cartItem.food?.title
        image.load(cartItem.food?.image.toString())
        price.text = "$" + cartItem.totalPrice.toString()
        countText.text = cartItem.count.toString()

        deleteBtn.setOnClickListener {
            onDelete(cartItem)
        }
    }


    fun addToCart() {
        val sharedPreferences = context.getSharedPreferences("cart", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cart", gson.toJson(Cart(items = listOf())))
        val cart: Cart = gson.fromJson(json, Cart::class.java)

        val existingItem = cart.items.find { it.food?.id == cartItem.food?.id }

        val updatedItems = if (existingItem != null) {
            cart.items.map {
                if (it.food?.id == cartItem.food?.id) {
                    it.copy(
                        count = cartItem.count,
                        totalPrice = cartItem.totalPrice
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
        onPriceUpdated?.invoke()
    }
}