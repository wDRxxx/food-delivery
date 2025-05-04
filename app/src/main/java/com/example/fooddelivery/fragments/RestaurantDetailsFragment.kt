package com.example.fooddelivery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.items.FoodItem
import com.example.fooddelivery.models.Category
import com.example.fooddelivery.models.FastFood
import com.example.fooddelivery.models.Restaurant
import com.example.fooddelivery.models.fastfoods

class RestaurantDetailsFragment : Fragment() {

    private lateinit var backBtn: ImageButton

    private lateinit var restaurant: Restaurant
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var rating: TextView
    private lateinit var delivery: TextView
    private lateinit var deliveryTime: TextView
    private lateinit var categoryTitle: TextView

    private lateinit var categoriesContainer: LinearLayout

    private lateinit var currentCategoryView: View
    private var currentCategory: Category? = null
    private lateinit var categoryFood: List<FastFood>

    private lateinit var foodGrid: GridLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_details, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        title = view.findViewById(R.id.title)
        image = view.findViewById(R.id.image)
        rating = view.findViewById(R.id.rating)
        delivery = view.findViewById(R.id.delivery)
        deliveryTime = view.findViewById(R.id.deliveryTime)
        categoryTitle = view.findViewById(R.id.categoryTitle)

        categoriesContainer = view.findViewById(R.id.categoriesContainer)

        foodGrid = view.findViewById(R.id.foodGrid)

        loadDetails()

        return view
    }

    fun loadDetails() {
        var restaurant: Restaurant = arguments?.getSerializable("restaurant") as Restaurant
        this.restaurant = restaurant
        addDetailsToView(restaurant)
    }

    fun addDetailsToView(restaurant: Restaurant) {
        title.text = restaurant.title
        image.load(restaurant.image.toString())
        delivery.text = restaurant.delivery
        deliveryTime.text = restaurant.deliveryTime
        rating.text = restaurant.rating.toString()

        addCategoriesToView(restaurant.categories)
    }

    fun addCategoriesToView(categories: List<Category>?) {
        categoriesContainer.removeAllViews()

        if (categories != null) {
            val inflater = LayoutInflater.from(requireContext())
            for (category in categories) {
                val view =
                    inflater.inflate(R.layout.item_rounded_border, categoriesContainer, false)

                if (categories.indexOf(category) == 0) {
                    currentCategory = category

                    currentCategoryView = view
                    currentCategoryView.findViewById<FrameLayout>(R.id.base)
                        .setBackgroundResource(R.drawable.rounded_accent)
                    currentCategoryView.findViewById<TextView>(R.id.title)
                        .setTextColor(
                            resources.getColor(R.color.white)
                        )

                    categoryFood =
                        getFastFoodByCategory(fastfoods, currentCategory?.id ?: 0)

                    categoryTitle.text = currentCategory?.title + " (" + categoryFood.size + ")"
                    addFoodToView(categoryFood)
                }

                view.setOnClickListener {
                    currentCategoryView.findViewById<FrameLayout>(R.id.base)
                        .setBackgroundResource(R.drawable.round_bordered)
                    currentCategoryView.findViewById<TextView>(R.id.title)
                        .setTextColor(
                            resources.getColor(R.color.primary_text)
                        )

                    currentCategoryView = view
                    currentCategoryView.findViewById<FrameLayout>(R.id.base)
                        .setBackgroundResource(R.drawable.rounded_accent)
                    currentCategoryView.findViewById<TextView>(R.id.title)
                        .setTextColor(
                            resources.getColor(R.color.white)
                        )

                    currentCategory =
                        categories.find { it.title == currentCategoryView.findViewById<TextView>(R.id.title).text }

                    categoryFood =
                        getFastFoodByCategory(fastfoods, currentCategory?.id ?: 0)

                    categoryTitle.text = currentCategory?.title + " (" + categoryFood.size + ")"
                    addFoodToView(categoryFood)
                }

                val titleView = view.findViewById<TextView>(R.id.title)
                titleView.text = category.title

                categoriesContainer.addView(view)
            }

        }
    }

    fun addFoodToView(foods: List<FastFood>) {
        foodGrid.removeAllViews()

        for (food in foods) {
            val fragment = FoodDetailsFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("food", food)
            }

            val foodItem = FoodItem(requireContext())
            foodItem.bind(food) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            val layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
                setMargins(0, 10, 0, 0)
            }
            foodItem.layoutParams = layoutParams
            foodGrid.addView(foodItem)
        }
    }

    fun getFastFoodByCategory(
        allFastFood: List<FastFood>,
        categoryId: Int
    ): List<FastFood> {
        return allFastFood.filter {
            it.restaurant?.id == restaurant.id && it.category.id == categoryId
        }
    }
}