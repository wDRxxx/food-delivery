package com.example.fooddelivery.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.Address
import com.example.fooddelivery.models.Addresses
import com.google.gson.Gson

class AddAddressFragment : Fragment() {

    private lateinit var backBtn: ImageView

    private lateinit var editAddress: EditText
    private lateinit var editStreet: EditText
    private lateinit var editPostCode: EditText
    private lateinit var editAppartment: EditText

    private lateinit var addBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_address, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        editAddress = view.findViewById(R.id.editTextAddress)
        editStreet = view.findViewById(R.id.editTextStreet)
        editPostCode = view.findViewById(R.id.editTextCode)
        editAppartment = view.findViewById(R.id.editTextAppartment)

        addBtn = view.findViewById(R.id.addBtn)
        addBtn.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("addresses", MODE_PRIVATE)
            val gson = Gson()
            val json =
                sharedPreferences.getString("addresses", gson.toJson(Addresses(items = listOf())))
            val addresses: Addresses = gson.fromJson(json, Addresses::class.java)

            addresses.items = addresses.items.plus(
                Address(
                    type = "Home",
                    address = editAddress.text.toString() + " "
                            + editStreet.text.toString() + " "
                            + editPostCode.text.toString() + " "
                            + editAppartment.text.toString()
                )
            )

            sharedPreferences.edit {
                putString("addresses", gson.toJson(addresses))
            }

            parentFragmentManager.popBackStack()
        }


        return view
    }
}