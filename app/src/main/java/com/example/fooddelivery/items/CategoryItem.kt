package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Category

class CategoryItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var base: FrameLayout
    private var title: TextView
    private var image: ImageView

    private var clickListener: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, this, true)

        base = findViewById(R.id.base)
        title = findViewById(R.id.title)
        image = findViewById(R.id.image)

        view.setOnClickListener {
            clickListener?.invoke()
        }
    }

    fun bind(category: Category, onClick: () -> Unit) {
        title.text = category.title
        image.load(category.image.toString())
        if (category.title != "All") {
            clickListener = onClick
        } else {
            base.setBackgroundResource(R.drawable.rounded_category_active)
        }
    }
}