package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.SearchHistoryAdapter
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.items.RestaurantSearchItem
import com.example.fooddelivery.models.Category
import com.example.fooddelivery.models.Restaurant

class SearchFragment : Fragment() {

    private lateinit var context: Context
    private lateinit var backBtn: ImageButton
    private lateinit var editText: EditText
    private lateinit var historyRecyclerView: RecyclerView
    private var historyList = mutableListOf<String>()
    private lateinit var historyAdapter: SearchHistoryAdapter
    private val handler = android.os.Handler()
    private var searchRunnable: Runnable? = null

    private lateinit var restaurantsContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        var view = inflater.inflate(R.layout.fragment_search, container, false)

        restaurantsContainer = view.findViewById(R.id.restaurantsContainer)

        loadRestaurants()

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        editText = view.findViewById(R.id.search)
        historyRecyclerView = view.findViewById(R.id.historyRecyclerView)

        var textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()

                searchRunnable?.let { handler.removeCallbacks(it) }

                if (query.isNotEmpty()) {
                    searchRunnable = Runnable { addToHistory(query) }
                    handler.postDelayed(searchRunnable!!, 2000)
                } else {
                    showHistory()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        historyAdapter = SearchHistoryAdapter(historyList) { query ->
            editText.removeTextChangedListener(textWatcher)
            editText.setText(query)
            editText.setSelection(editText.text.length)
            editText.addTextChangedListener(textWatcher)
        }
        historyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        historyRecyclerView.adapter = historyAdapter

        loadHistory()
        showHistory()

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()

                searchRunnable?.let { handler.removeCallbacks(it) }

                if (query.isNotEmpty()) {
                    searchRunnable = Runnable { addToHistory(query) }
                    handler.postDelayed(searchRunnable!!, 2000)
                } else {
                    showHistory()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return view
    }

    private fun loadHistory() {
        val sharedPreferences = context.getSharedPreferences("SearchHistory", MODE_PRIVATE)


        val savedHistory = sharedPreferences.getString("history", "")
        historyList.clear()
        if (!savedHistory.isNullOrEmpty()) {

            Log.println(Log.INFO, "SIGMA", savedHistory.split("||").toString())
            historyList.addAll(savedHistory.split("||"))
        }

        historyAdapter.notifyDataSetChanged()
    }

    private fun saveHistory() {
        val sharedPreferences = context.getSharedPreferences("SearchHistory", MODE_PRIVATE)
        val historyString =
            historyList.joinToString(separator = "||")
        sharedPreferences.edit {
            putString("history", historyString)
        }
    }

    private fun addToHistory(query: String) {
        if (query.isBlank()) return

        historyList.remove(query)

        historyList.add(0, query)

        if (historyList.size > 10) {
            historyList = historyList.take(10).toMutableList()
        }

        saveHistory()
        historyAdapter.notifyDataSetChanged()
    }

    private fun showHistory() {
        historyRecyclerView.visibility = if (historyList.isNotEmpty()) View.VISIBLE else View.GONE
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
            val restaurantItem = RestaurantSearchItem(requireActivity())
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
}