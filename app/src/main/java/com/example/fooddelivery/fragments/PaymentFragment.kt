package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.models.CardItem
import com.example.fooddelivery.models.Cards
import com.example.fooddelivery.models.Cart
import com.example.fooddelivery.models.OrderItem
import com.example.fooddelivery.models.Orders
import com.example.fooddelivery.models.PaymentType
import com.example.fooddelivery.models.paymentTypes
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class PaymentFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var backBtn: ImageButton

    private lateinit var paymentTypesContainer: LinearLayout
    private lateinit var activePaymentTypeItem: com.example.fooddelivery.items.PaymentType
    private lateinit var activePaymentType: PaymentType

    private lateinit var cardsContainer: LinearLayout
    private var activeCardItem: com.example.fooddelivery.items.CardItem? = null
    private var activeCard: CardItem? = null
    private lateinit var noCard: LinearLayout

    private lateinit var addBtn: LinearLayout
    private lateinit var nextBtn: Button

    private lateinit var priceText: TextView
    private lateinit var price: TextView

    lateinit var source: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        source = arguments?.getString("source").toString()

        price = view.findViewById(R.id.price)
        priceText = view.findViewById(R.id.priceText)

        noCard = view.findViewById(R.id.noCard)
        addBtn = view.findViewById(R.id.addBtn)
        cardsContainer = view.findViewById(R.id.cardsContainer)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        nextBtn = view.findViewById(R.id.nextBtn)
        if (source !== "cart") {
            nextBtn.text = "Save"
            price.visibility = GONE
            priceText.visibility = GONE
        } else {
            price.text = arguments?.getString("price").toString()
        }
        nextBtn.setOnClickListener {
            if (source !== "cart") {
                parentFragmentManager.popBackStack()
                return@setOnClickListener
            }

            if (activePaymentType == paymentTypes[0]) {
                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                saveOrder()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SuccessPaymentFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                if (activeCard != null) {
                    parentFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )

                    saveOrder()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SuccessPaymentFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        paymentTypesContainer = view.findViewById(R.id.paymentTypesContainer)
        loadPaymentTypes()

        noCard.visibility = GONE
        addBtn.visibility = GONE

        addBtn.setOnClickListener {
            val fragment = AddCardFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("paymentType", activePaymentType)
                putString("source", source)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    fun loadPaymentTypes() {
        addPaymentTypesToView(paymentTypes)
    }

    fun addPaymentTypesToView(paymentTypes: List<PaymentType>) {
        paymentTypesContainer.removeAllViews()

        for (paymentType in paymentTypes) {
            if (source !== "cart" && paymentType.title == "Cash") {
                continue
            }

            val paymentTypeItem = com.example.fooddelivery.items.PaymentType(context)
            paymentTypeItem.bind(paymentType)
            if (paymentTypes.indexOf(paymentType) == 0 && source === "cart") {
                paymentTypeItem.toggleActive()
                activePaymentTypeItem = paymentTypeItem
                activePaymentType = paymentType
            }
            if (paymentTypes.indexOf(paymentType) == 1 && source !== "cart") {
                paymentTypeItem.toggleActive()
                activePaymentTypeItem = paymentTypeItem
                activePaymentType = paymentType

                val cards = paymentTypeCards(activePaymentType)
                if (cards.isEmpty()) {
                    noCard.visibility = VISIBLE
                } else {
                    noCard.visibility = GONE

                    addCardsToView(cards)
                }
            }

            paymentTypeItem.setOnClickListener {

                cardsContainer.removeAllViews()
                activeCard = null
                paymentTypeItem.toggleActive()

                activePaymentTypeItem.toggleActive()
                activePaymentTypeItem = paymentTypeItem
                activePaymentType = paymentType

                if (activePaymentType == com.example.fooddelivery.models.paymentTypes[0]) {
                    noCard.visibility = GONE
                    addBtn.visibility = GONE
                } else {
                    addBtn.visibility = VISIBLE

                    val cards = paymentTypeCards(activePaymentType)
                    if (cards.isEmpty()) {
                        noCard.visibility = VISIBLE
                    } else {
                        noCard.visibility = GONE

                        addCardsToView(cards)
                    }
                }
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                context.dpToPx(20f), // Отступ справа
                0
            )

            paymentTypeItem.layoutParams = params
            paymentTypesContainer.addView(paymentTypeItem)
        }
    }

    fun paymentTypeCards(type: PaymentType): List<CardItem> {
        val sharedPreferences = context.getSharedPreferences("cards", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cards", gson.toJson(Cards(items = listOf())))
        val cards: Cards = gson.fromJson(json, Cards::class.java)

        return cards.items.filter { it.type == type }
    }

    fun addCardsToView(cards: List<CardItem>) {
        cardsContainer.removeAllViews()

        for (card in cards) {
            val cardItem = com.example.fooddelivery.items.CardItem(context)
            cardItem.bind(card)
            if (cards.indexOf(card) == 0) {
                cardItem.toggleActive()
                activeCardItem = cardItem
                activeCard = card
            }

            cardItem.setOnClickListener {
                cardItem.toggleActive()

                activeCardItem?.toggleActive()
                activeCardItem = cardItem
                activeCard = card
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                0, // Отступ справа
                context.dpToPx(20f),
            )

            cardItem.layoutParams = params
            cardsContainer.addView(cardItem)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveOrder() {
        val sharedPreferences = context.getSharedPreferences("orders", MODE_PRIVATE)
        val cartPreferences = context.getSharedPreferences("cart", MODE_PRIVATE)
        val gson = Gson()

        val cartJson = cartPreferences.getString("cart", gson.toJson(Cart(items = listOf())))
        val cart: Cart = gson.fromJson(cartJson, Cart::class.java)

        val ordersJson =
            sharedPreferences.getString("orders", gson.toJson(Orders(items = listOf())))
        var orders: Orders = gson.fromJson(ordersJson, Orders::class.java)

        val newOrder = OrderItem(
            id = (orders.items.maxOfOrNull { it.id } ?: 0) + 1,
            date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM, HH:mm", Locale.ENGLISH)),
            items = cart.items
        )

        val updatedOrders = Orders(items = orders.items + newOrder)

        Log.println(Log.INFO, "orders-updated", updatedOrders.toString())

        sharedPreferences.edit {
            putString("orders", gson.toJson(updatedOrders))
        }
        cartPreferences.edit {
            putString("cart", gson.toJson(Cart(items = listOf())))
        }
    }
}