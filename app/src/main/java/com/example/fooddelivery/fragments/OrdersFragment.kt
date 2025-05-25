package com.example.fooddelivery.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.models.Orders
import com.google.gson.Gson

class OrdersFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var backBtn: ImageButton
    private lateinit var ordersContainer: LinearLayout

    private lateinit var ongoingTab: LinearLayout
    private lateinit var ongoingText: TextView
    private lateinit var ongoingView: View

    private lateinit var historyTab: LinearLayout
    private lateinit var historyText: TextView
    private lateinit var historyView: View

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()

        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        ordersContainer = view.findViewById(R.id.ordersContainer)
        loadOrders()

        ongoingTab = view.findViewById(R.id.ongoingTab)
        ongoingText = view.findViewById(R.id.ongoingText)
        ongoingView = view.findViewById(R.id.ongoingIndicator)

        historyTab = view.findViewById(R.id.historyTab)
        historyText = view.findViewById(R.id.historyText)
        historyView = view.findViewById(R.id.historyIndicator)

        ongoingTab.setOnClickListener {
            ongoingText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent))
            ongoingView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.accent))

            historyText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            historyView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

            loadOrders()
        }

        historyTab.setOnClickListener {
            historyText.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent))
            historyView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.accent))

            ongoingText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            ongoingView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

            loadActiveOrders()
        }
        return view
    }

    fun loadOrders() {
        val sharedPreferences = context.getSharedPreferences("orders", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("orders", gson.toJson(Orders(items = listOf())))
        val orders: Orders = gson.fromJson(json, Orders::class.java)

        Log.println(Log.INFO, "orders", orders.toString())
        addOrdersToView(orders)
    }

    fun addOrdersToView(orders: Orders) {
        ordersContainer.removeAllViews()

        for (item in orders.items) {
            if (item.items.size < 1) {
                continue
            }

            val orderItem = com.example.fooddelivery.items.OrderItem(context)
            orderItem.bind(item)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                0, // Отступ справа
                requireActivity().dpToPx(30f)
            )

            orderItem.layoutParams = params
            ordersContainer.addView(orderItem)
        }
    }

    fun loadActiveOrders() {
        val sharedPreferences = context.getSharedPreferences("orders", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("orders", gson.toJson(Orders(items = listOf())))
        val orders: Orders = gson.fromJson(json, Orders::class.java)

        Log.println(Log.INFO, "orders", orders.toString())
        addActiveOrdersToView(orders)
    }

    fun addActiveOrdersToView(orders: Orders) {
        ordersContainer.removeAllViews()

        for (item in orders.items) {
            if (item.items.size < 1) {
                continue
            }

            val orderItem = com.example.fooddelivery.items.ActiveOrderItem(context)
            orderItem.bind(item)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                0, // Отступ справа
                requireActivity().dpToPx(30f)
            )

            orderItem.layoutParams = params
            ordersContainer.addView(orderItem)
        }
    }
}