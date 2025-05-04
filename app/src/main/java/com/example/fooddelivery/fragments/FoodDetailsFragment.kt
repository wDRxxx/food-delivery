package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Cart
import com.example.fooddelivery.models.CartItem
import com.example.fooddelivery.models.FastFood
import com.google.gson.Gson

class FoodDetailsFragment : Fragment() {

    private lateinit var context: Context
    private lateinit var backBtn: ImageButton

    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var restaurant: TextView
    private lateinit var rating: TextView
    private lateinit var delivery: TextView
    private lateinit var deliveryTime: TextView
    private lateinit var price: TextView

    private var cartItem: CartItem = CartItem(
        count = 1,
        food = null,
        totalPrice = 0f
    )
    private lateinit var countText: TextView
    private lateinit var minusBtn: TextView
    private lateinit var plusBtn: TextView
    private lateinit var addToCartBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_food_details, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        title = view.findViewById(R.id.title)
        image = view.findViewById(R.id.image)
        restaurant = view.findViewById(R.id.resturant)
        rating = view.findViewById(R.id.rating)
        delivery = view.findViewById(R.id.delivery)
        deliveryTime = view.findViewById(R.id.deliveryTime)
        price = view.findViewById(R.id.price)

        countText = view.findViewById(R.id.count)
        minusBtn = view.findViewById(R.id.minusBtn)
        minusBtn.setOnClickListener {
            if (cartItem.count > 1) {
                cartItem.count--
                cartItem.totalPrice -= cartItem.food?.price!!

                countText.text = cartItem.count.toString()
                price.text = cartItem.totalPrice.toString() + "$"
            }
        }
        plusBtn = view.findViewById(R.id.plusBtn)
        plusBtn.setOnClickListener {
            cartItem.count++
            cartItem.totalPrice += cartItem.food?.price!!

            countText.text = cartItem.count.toString()
            price.text = cartItem.totalPrice.toString() + "$"
        }

        addToCartBtn = view.findViewById(R.id.addToCartBtn)
        addToCartBtn.setOnClickListener {
            addToCart()
        }

        loadDetails()

        return view
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

    fun loadDetails() {
        var food: FastFood = arguments?.getSerializable("food") as FastFood
        cartItem.food = food
        cartItem.totalPrice = food.price!!
        addDetailsToView(food)
    }

    fun addDetailsToView(food: FastFood) {
        title.text = food.title
        image.load(food.image.toString())
        restaurant.text = food.restaurant?.title
        delivery.text = food.restaurant?.delivery
        deliveryTime.text = food.restaurant?.deliveryTime
        rating.text = food.restaurant?.rating.toString()
        price.text = food.price.toString() + "$"
    }
}