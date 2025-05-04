package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.models.CardItem
import com.example.fooddelivery.models.Cards
import com.example.fooddelivery.models.PaymentType
import com.example.fooddelivery.models.paymentTypes
import com.google.gson.Gson

class PaymentFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var backBtn: ImageButton

    private lateinit var paymentTypesContainer: LinearLayout
    private lateinit var activePaymentTypeItem: com.example.fooddelivery.items.PaymentType
    private lateinit var activePaymentType: PaymentType

    private lateinit var cardsContainer: LinearLayout
    private lateinit var noCard: LinearLayout
    private lateinit var addBtn: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        paymentTypesContainer = view.findViewById(R.id.paymentTypesContainer)
        loadPaymentTypes()

        cardsContainer = view.findViewById(R.id.cardsContainer)

        noCard = view.findViewById(R.id.noCard)
        noCard.visibility = GONE
        addBtn = view.findViewById(R.id.addBtn)
        addBtn.visibility = GONE

        addBtn.setOnClickListener {
            val fragment = AddCardFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("paymentType", activePaymentType)
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
            val paymentTypeItem = com.example.fooddelivery.items.PaymentType(context)
            paymentTypeItem.bind(paymentType)
            if (paymentTypes.indexOf(paymentType) == 0) {
                paymentTypeItem.toggleActive()
                activePaymentTypeItem = paymentTypeItem
                activePaymentType = paymentType
            }

            paymentTypeItem.setOnClickListener {

                cardsContainer.removeAllViews()
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
}