package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.CardItem
import com.example.fooddelivery.models.Cards
import com.example.fooddelivery.models.PaymentType
import com.google.gson.Gson

class AddCardFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var backBtn: ImageButton

    private lateinit var editName: EditText
    private lateinit var editNumber: EditText
    private lateinit var editDate: EditText
    private lateinit var editCvc: EditText

    private lateinit var nextBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_add_cart, container, false)

        val source: String = arguments?.getString("source").toString()

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            if (source != "payment") {
                parentFragmentManager.popBackStack()
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SuccessPaymentFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        editName = view.findViewById(R.id.editName)
        editNumber = view.findViewById(R.id.editNumber)
        editDate = view.findViewById(R.id.editDate)
        editCvc = view.findViewById(R.id.editCvc)

        nextBtn = view.findViewById(R.id.nextBtn)
        nextBtn.setOnClickListener {
            saveCard()
            parentFragmentManager.popBackStack()
        }

        return view
    }

    fun saveCard() {
        var paymentType: PaymentType = arguments?.getSerializable("paymentType") as PaymentType
        Log.println(Log.INFO, "paymentType", paymentType.toString())
        val sharedPreferences = context.getSharedPreferences("cards", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cards", gson.toJson(Cards(items = listOf())))
        val cards: Cards = gson.fromJson(json, Cards::class.java)

        cards.items = cards.items.plus(
            CardItem(
                type = paymentType,
                number = editNumber.text.toString(),
                expireDate = editDate.text.toString(),
                cvc = editCvc.text.toString()
            )
        )

        sharedPreferences.edit {
            putString("cards", gson.toJson(cards))
        }
    }
}