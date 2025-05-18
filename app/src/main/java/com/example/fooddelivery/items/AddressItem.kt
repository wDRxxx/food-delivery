package com.example.fooddelivery.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Address

class AddressItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var address: Address

    private var type: TextView
    private var image: ImageButton
    private var addressText: TextView

    private var editBtn: ImageButton
    private var deleteBtn: ImageButton

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_address, this, true)

        image = findViewById(R.id.image)
        type = findViewById(R.id.type)
        addressText = findViewById(R.id.address)

        editBtn = view.findViewById(R.id.editBtn)
        deleteBtn = view.findViewById(R.id.deleteBtn)
    }

    fun bind(
        address: Address,
        onDelete: (Address) -> Unit
    ) {
        this.address = address

        type.text = address.type
        addressText.text = address.address
        deleteBtn.setOnClickListener {
            onDelete(address)
        }
        
        if (address.type == "Home") {
            image.load(R.drawable.icon_home)
        }
    }
}