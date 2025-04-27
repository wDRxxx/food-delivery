package com.example.fooddelivery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.items.CategoryItem
import com.example.fooddelivery.items.RestaurantItem
import com.example.fooddelivery.models.Category
import com.example.fooddelivery.models.Restaurant


class HomeFragment : Fragment() {

    lateinit var categoriesContainer: LinearLayout
    lateinit var restaurantsContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, DetailsFragment())
//            .addToBackStack(null)
//            .commit()
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        categoriesContainer = view.findViewById(R.id.categoriesContainer)
        restaurantsContainer = view.findViewById(R.id.restaurantsContainer)

        loadCategories()
        loadRestaurants()

        return view
    }

    private fun loadRestaurants() {
        val restaurants: List<Restaurant> = listOf(
            Restaurant(
                title = "Rose Garden Restaurant",
                categories = listOf(
                    Category(
                        title = "Pizza",
                    ),
                    Category(
                        title = "Hot Dog",
                    ),
                ),
                rating = 4.7f,
                delivery = "free",
                deliveryTime = "20 min"
            ),
            Restaurant(
                title = "Sigma Restaurant",
                categories = listOf(
                    Category(
                        title = "Burger",
                    ),
                    Category(
                        title = "Pizza",
                    ),
                    Category(
                        title = "Hot Dog",
                    ),
                ),
                rating = 5f,
                delivery = "1$",
                deliveryTime = "10 min"
            )
        )

        addRestaurantsToView(restaurants)
    }

    private fun addRestaurantsToView(restaurants: List<Restaurant>) {
        restaurantsContainer.removeAllViews()

        for (restaurant in restaurants) {
            val restaurantItem = RestaurantItem(requireActivity())
            restaurantItem.bind(restaurant)

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
                title = "All",
            ),
            Category(
                title = "Pizza",
            ),
            Category(
                title = "Hot Dog",
            ),
        )

        addCategoriesToView(categories)
    }

    private fun addCategoriesToView(categories: List<Category>) {
        categoriesContainer.removeAllViews()

        for (category in categories) {
            val categoryItem = CategoryItem(requireActivity())
            categoryItem.bind(category.title)

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
