package com.example.fooddelivery.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.FastFood

class FoodDetailsFragment : Fragment() {

    private lateinit var backBtn: ImageButton

    private lateinit var title: TextView
    private lateinit var restaurant: TextView
    private lateinit var rating: TextView
    private lateinit var delivery: TextView
    private lateinit var deliveryTime: TextView
    private lateinit var price: TextView

    private var count: Int = 0
    private lateinit var countText: TextView
    private lateinit var minusBtn: TextView
    private lateinit var plusBtn: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_details, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        title = view.findViewById(R.id.title)
        restaurant = view.findViewById(R.id.resturant)
        rating = view.findViewById(R.id.rating)
        delivery = view.findViewById(R.id.delivery)
        deliveryTime = view.findViewById(R.id.deliveryTime)
        price = view.findViewById(R.id.price)

        countText = view.findViewById(R.id.count)
        minusBtn = view.findViewById(R.id.minusBtn)
        minusBtn.setOnClickListener {
            Log.println(Log.INFO, "count minus", count.toString())
            if (count > 1) {
                count--
                countText.text = count.toString()
            }
        }
        plusBtn = view.findViewById(R.id.plusBtn)
        plusBtn.setOnClickListener {
            Log.println(Log.INFO, "count plus", count.toString())
            count++
            countText.text = count.toString()
        }

        loadDetails()

        return view
    }

    fun loadDetails() {
        var food: FastFood = arguments?.getSerializable("food") as FastFood

        addDetailsToView(food)
    }

    fun addDetailsToView(food: FastFood) {
        title.text = food.title
        restaurant.text = food.restaurant?.title
        delivery.text = food.restaurant?.delivery
        deliveryTime.text = food.restaurant?.deliveryTime
        rating.text = food.restaurant?.rating.toString()
        price.text = food.price.toString() + "$"
    }
}