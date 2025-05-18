package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.dpToPx
import com.example.fooddelivery.models.Address
import com.example.fooddelivery.models.Addresses
import com.google.gson.Gson

class AddressFragment : Fragment() {
    private lateinit var context: Context

    private lateinit var backBtn: ImageButton
    private lateinit var itemsContainer: LinearLayout

    private lateinit var addBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = requireContext()
        val view = inflater.inflate(R.layout.fragment_addresses, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        addBtn = view.findViewById(R.id.addBtn)
        addBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddAddressFragment())
                .addToBackStack(null)
                .commit()
        }

        itemsContainer = view.findViewById(R.id.addressesContainer)

        loadAddresses()

        return view
    }

    fun loadAddresses() {
        val sharedPreferences = context.getSharedPreferences("addresses", MODE_PRIVATE)
        val gson = Gson()
        val json =
            sharedPreferences.getString("addresses", gson.toJson(Addresses(items = listOf())))
        val addresses: Addresses = gson.fromJson(json, Addresses::class.java)
        Log.println(Log.INFO, "addresses", addresses.toString())

        addItemsToView(addresses.items)
    }

    fun addItemsToView(items: List<Address>) {
        itemsContainer.removeAllViews()

        for (item in items) {
            val addressItem = com.example.fooddelivery.items.AddressItem(context)
            addressItem.bind(
                address = item,
                onDelete = { itemToDelete ->
                    itemsContainer.removeView(addressItem)

                    val sharedPreferences = context.getSharedPreferences("addresses", MODE_PRIVATE)
                    val gson = Gson()
                    val json =
                        sharedPreferences.getString(
                            "addresses",
                            gson.toJson(Addresses(emptyList()))
                        )
                    val addresses = gson.fromJson(json, Addresses::class.java)

                    val updatedItems = addresses.items.filter { it.address != itemToDelete.address }
                    addresses.items = updatedItems

                    sharedPreferences.edit {
                        putString("addresses", gson.toJson(addresses))
                    }
                })

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                0, // Отступ слева
                0,
                0, // Отступ справа
                requireActivity().dpToPx(20f)
            )

            addressItem.layoutParams = params
            itemsContainer.addView(addressItem)
        }
    }
}