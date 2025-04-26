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
import com.example.fooddelivery.models.Category


class HomeFragment : Fragment() {

    lateinit var categoriesContainer: LinearLayout

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

        loadCategories()

        return view
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
