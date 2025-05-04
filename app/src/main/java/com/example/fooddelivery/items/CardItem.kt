package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.CardItem

class CardItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var cardItem: CardItem

    private var image: ImageView
    private var number: TextView
    private var title: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, this, true)

        image = view.findViewById(R.id.image)
        number = view.findViewById(R.id.number)
        title = view.findViewById(R.id.title)
    }

    fun bind(
        cardItem: CardItem
    ) {
        this.cardItem = cardItem

        image.setBackgroundResource(cardItem.type.imageId)
        title.text = cardItem.type.title
        number.text = maskString(cardItem.number)
    }

    fun maskString(input: String): String {
        if (input.length <= 2) return input
        val maskedPart = "*".repeat(input.length - 2)
        val visiblePart = input.takeLast(2)
        return maskedPart + visiblePart
    }
}