package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.models.Cart
import com.example.fooddelivery.models.CartItem
import com.google.gson.Gson

class CartFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var backBtn: ImageButton
    private lateinit var itemsContainer: LinearLayout

    private lateinit var price: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        itemsContainer = view.findViewById(R.id.itemsContainer)

        price = view.findViewById(R.id.price)

        loadCart()

        return view
    }

    fun loadCart() {
        val sharedPreferences = context.getSharedPreferences("cart", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cart", gson.toJson(Cart(items = listOf())))
        val cart: Cart = gson.fromJson(json, Cart::class.java)

        addItemsToCart(cart.items)
        updateTotalPriceText()
    }

    fun addItemsToCart(items: List<CartItem>) {
        itemsContainer.removeAllViews()

        for (item in items) {

            val cartItem = com.example.fooddelivery.items.CartItem(context)
            cartItem.bind(item) {
                updateTotalPriceText()
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                0, // Отступ справа
                requireActivity().dpToPx(20f)
            )

            cartItem.layoutParams = params
            itemsContainer.addView(cartItem)
        }
    }

    private fun updateTotalPriceText() {
        val sharedPreferences = requireContext().getSharedPreferences("cart", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cart", gson.toJson(Cart(items = listOf())))
        val cart: Cart = gson.fromJson(json, Cart::class.java)

        val total = cart.items.sumOf { it.totalPrice.toDouble() }
        price.text = "$" + total.toString()
    }
}