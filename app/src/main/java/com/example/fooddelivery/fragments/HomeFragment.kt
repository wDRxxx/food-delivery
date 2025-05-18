package com.example.fooddelivery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.items.CategoryItem
import com.example.fooddelivery.items.RestaurantHomeItem
import com.example.fooddelivery.models.Category
import com.example.fooddelivery.models.Restaurant
import com.example.fooddelivery.models.categories
import com.example.fooddelivery.models.restaurants


class HomeFragment : Fragment() {
    lateinit var cartBtn: ImageButton
    lateinit var menuBtn: ImageButton

    lateinit var categoriesContainer: LinearLayout
    lateinit var restaurantsContainer: LinearLayout

    lateinit var searchBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        cartBtn = view.findViewById(R.id.cartBtn)
        cartBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddressFragment())
                .addToBackStack(null)
                .commit()
        }

        menuBtn = view.findViewById(R.id.menuBtn)
        menuBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MenuFragment())
                .addToBackStack(null)
                .commit()
        }

        categoriesContainer = view.findViewById(R.id.categoriesContainer)
        restaurantsContainer = view.findViewById(R.id.restaurantsContainer)
        searchBtn = view.findViewById(R.id.search)

        searchBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        loadCategories()
        loadRestaurants()

        return view
    }

    private fun loadRestaurants() {
        val restaurants: List<Restaurant> = restaurants

        addRestaurantsToView(restaurants)
    }

    private fun addRestaurantsToView(restaurants: List<Restaurant>) {
        restaurantsContainer.removeAllViews()

        for (restaurant in restaurants) {
            val fragment = RestaurantDetailsFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("restaurant", restaurant)
            }

            val restaurantItem = RestaurantHomeItem(requireActivity())
            restaurantItem.bind(restaurant) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
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

            restaurantItem.layoutParams = params
            restaurantsContainer.addView(restaurantItem)
        }
    }

    private fun loadCategories() {
        val categories: List<Category> = listOf(
            Category(
                id = 0,
                title = "All",
                image = ""
            ),
        ).plus(categories)

        addCategoriesToView(categories)
    }

    private fun addCategoriesToView(categories: List<Category>) {
        categoriesContainer.removeAllViews()

        for (category in categories) {
            val categoryItem = CategoryItem(requireActivity())

            val fragment = CategoryFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("category", category)
            }
            categoryItem.bind(category) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                requireActivity().dpToPx(20f), // Отступ справа
                0
            )

            categoryItem.layoutParams = params
            categoriesContainer.addView(categoryItem)
        }
    }
}
