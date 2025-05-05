package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Cart
import com.example.fooddelivery.models.OrderItem
import com.example.fooddelivery.models.Orders
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SuccessPaymentFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var nextBtn: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_success_payment, container, false)

        nextBtn = view.findViewById(R.id.nextBtn)
        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .addToBackStack(null)
                .commit()
        }

        var sharedPreferences = context.getSharedPreferences("cart", MODE_PRIVATE)
        val gson = Gson()
        var json = sharedPreferences.getString("cart", gson.toJson(Cart(emptyList())))
        val cart = gson.fromJson(json, Cart::class.java)
        sharedPreferences.edit {
            putString("cart", gson.toJson(Cart(items = listOf())))
        }

        sharedPreferences = context.getSharedPreferences("orders", MODE_PRIVATE)
        json = sharedPreferences.getString("cart", gson.toJson(Orders(emptyList())))
        val orders = gson.fromJson(json, Orders::class.java)
        val order = OrderItem(
            id = orders.items.size,
            date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM, HH:mm", Locale.ENGLISH)),
            items = cart.items
        )
        orders.items = orders.items.plus(order)

        sharedPreferences.edit {
            putString("orders", gson.toJson(orders))
        }

        return view
    }
}