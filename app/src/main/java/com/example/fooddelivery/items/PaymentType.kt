package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.models.PaymentType

class PaymentType @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var base: LinearLayout
    private var image: ImageView
    private var title: TextView

    var isActive: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.item_payment_type, this, true)

        base = findViewById(R.id.base)
        image = findViewById(R.id.image)
        title = findViewById(R.id.title)
    }

    fun bind(paymentType: PaymentType) {
        image.setBackgroundResource(paymentType.imageId)
        title.text = paymentType.title
    }

    fun toggleActive() {
        isActive = !isActive

        if (isActive) {
            base.setBackgroundResource(R.drawable.rounded_white_accent_bordered)
        } else {
            base.setBackgroundResource(R.drawable.rounded_light)
        }
    }
}