package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R

class CategoryItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_category, this, true)

        title = findViewById(R.id.title)
    }

    fun bind(category: String) {
        title.text = category
    }
}