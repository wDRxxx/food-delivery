package com.example.fooddelivery.models

import com.example.fooddelivery.R
import java.io.Serializable

data class PaymentType(
    val id: Int,
    val title: String,
    val imageId: Int
) : Serializable

val paymentTypes: List<PaymentType> = listOf(
    PaymentType(
        id = 1,
        title = "Cash",
        imageId = R.drawable.icon_cash
    ),
    PaymentType(
        id = 2,
        title = "Visa",
        imageId = R.drawable.icon_visa
    ),
    PaymentType(
        id = 3,
        title = "Mastercard",
        imageId = R.drawable.icon_mastercard
    ),
    PaymentType(
        id = 4,
        title = "PayPal",
        imageId = R.drawable.icon_paypal
    ),
)

data class CardItem(
    val type: PaymentType,
    val number: String,
    val expireDate: String,
    val cvc: String
) : Serializable

data class Cards(
    var items: List<CardItem>
) : Serializable
