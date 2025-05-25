package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.edit
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

    private lateinit var editBtn: TextView
    private var editing: Boolean = false

    private lateinit var nextBtn: Button

    var totalPrice = ""

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

        nextBtn = view.findViewById(R.id.nextBtn)
        nextBtn.setOnClickListener {
            val fragment = PaymentFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("source", "cart")
                putString("price", totalPrice)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        itemsContainer = view.findViewById(R.id.itemsContainer)

        price = view.findViewById(R.id.price)

        editBtn = view.findViewById(R.id.editBtn)
        editBtn.setOnClickListener {
            editing = !editing
            if (editing) {
                editBtn.text = "DONE"
                editBtn.setTextColor(Color.parseColor("#059C6A"))
            } else {
                editBtn.text = "EDIT ITEMS"
                editBtn.setTextColor(ContextCompat.getColor(context, R.color.accent))
            }

            for (i in 0 until itemsContainer.childCount) {
                val child = itemsContainer.getChildAt(i)

                val deleteBtn = child.findViewById<ImageView>(R.id.deleteBtn)
                if (editing) {
                    deleteBtn?.visibility = View.VISIBLE
                } else {
                    deleteBtn?.visibility = View.INVISIBLE
                }
            }
        }

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
            cartItem.bind(cartItem = item, onPriceUpdated = {
                updateTotalPriceText()
            }, onDelete = { itemToDelete ->
                itemsContainer.removeView(cartItem)

                val sharedPreferences = context.getSharedPreferences("cart", MODE_PRIVATE)
                val gson = Gson()
                val json = sharedPreferences.getString("cart", gson.toJson(Cart(emptyList())))
                val cart = gson.fromJson(json, Cart::class.java)

                val updatedItems = cart.items.filter { it.food?.id != itemToDelete.food?.id }
                cart.items = updatedItems

                sharedPreferences.edit {
                    putString("cart", gson.toJson(cart))
                }
                updateTotalPriceText()
            })

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
        val sharedPreferences = requireContext().getSharedPreferences("cart", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cart", gson.toJson(Cart(items = listOf())))
        val cart: Cart = gson.fromJson(json, Cart::class.java)

        val total = cart.items.sumOf { it.totalPrice.toDouble() }
        price.text = "$" + String.format("%.2f", total)
        totalPrice = "$" + String.format("%.2f", total)
    }
}