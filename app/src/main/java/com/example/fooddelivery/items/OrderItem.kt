package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.OrderItem

class OrderItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var orderItem: OrderItem

    private var title: TextView
    private var image: ImageView
    private var price: TextView
    private var count: TextView
    private var number: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, this, true)

        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
        price = findViewById(R.id.price)
        count = findViewById(R.id.count)
        number = findViewById(R.id.number)
    }

    fun bind(
        orderItem: OrderItem,
    ) {
        this.orderItem = orderItem

        val firstItem = orderItem.items[0]
        title.text = firstItem.food!!.title
        price.text = "%.2f".format(orderItem.items.sumOf { it.totalPrice.toDouble() }) + "$"
        count.text = orderItem.items.sumOf { it.count }.toString() + " Items"
        number.text = "#${orderItem.id}"
    }
}