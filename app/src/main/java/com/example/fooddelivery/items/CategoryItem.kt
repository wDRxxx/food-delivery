package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Category

class CategoryItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: TextView
    private var image: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_category, this, true)

        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
    }

    fun bind(category: Category) {
        title.text = category.title
        image.load(category.image.toString())
    }
}