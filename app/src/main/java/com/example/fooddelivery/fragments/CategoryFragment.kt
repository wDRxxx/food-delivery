package com.example.fooddelivery.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.items.FoodItem
import com.example.fooddelivery.items.RestaurantHomeItem
import com.example.fooddelivery.models.Category
import com.example.fooddelivery.models.FastFood
import com.example.fooddelivery.models.Restaurant
import com.example.fooddelivery.models.categories
import com.example.fooddelivery.models.fastfoods
import com.example.fooddelivery.models.restaurants

class CategoryFragment : Fragment() {
    lateinit var backBtn: ImageButton
    lateinit var searchBtn: ImageButton
    lateinit var filterBtn: ImageButton

    lateinit var categoryTitle: TextView

    lateinit var restaurantsContainer: LinearLayout

    private var currentCategory: Category? = null
    private lateinit var categoryFood: List<FastFood>

    private lateinit var foodGrid: GridLayout

    private lateinit var spinner: Spinner
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        searchBtn = view.findViewById(R.id.searchBtn)
        searchBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        filterBtn = view.findViewById(R.id.filterBtn)

        restaurantsContainer = view.findViewById(R.id.restaurantsContainer)

        foodGrid = view.findViewById(R.id.foodGrid)
        currentCategory = arguments?.getSerializable("category") as Category

        categoryTitle = view.findViewById(R.id.categoryTitle)

        spinner = view.findViewById(R.id.spinner)
        scrollView = view.findViewById(R.id.scrollView)

        loadRestaurants()
        loadSpinner()
        addFoodToView()

        return view
    }

    private fun addFoodToView() {
        foodGrid.removeAllViews()
        categoryTitle.text = "Popular " + currentCategory?.title + "s"
        val foods = getFastFoodByCategory(fastfoods, currentCategory!!.id)

        val params = scrollView.layoutParams
        if (foods.size == 0) {
            scrollView.visibility = GONE
            categoryTitle.visibility = GONE
        } else if (foods.size <= 2) {
            categoryTitle.visibility = VISIBLE
            scrollView.visibility = VISIBLE
            params.height = requireContext().dpToPx(200f)
            scrollView.layoutParams = params
        } else {
            categoryTitle.visibility = VISIBLE
            scrollView.visibility = VISIBLE
            params.height = requireContext().dpToPx(400f)
            scrollView.layoutParams = params
        }

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

    fun getFastFoodByCategory(
        allFastFood: List<FastFood>,
        categoryId: Int
    ): List<FastFood> {
        return allFastFood.filter {
            it.category.id == categoryId
        }
    }

    fun loadSpinner() {
        val adapter = CategorySpinnerAdapter(requireContext(), categories)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                currentCategory = categories[position]

                addFoodToView()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}

class CategorySpinnerAdapter(
    private val context: Context,
    private val categories: List<Category>
) : ArrayAdapter<Category>(context, android.R.layout.simple_spinner_item, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = categories[position].title
        return view
    }
}