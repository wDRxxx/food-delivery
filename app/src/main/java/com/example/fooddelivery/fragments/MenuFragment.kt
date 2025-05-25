package com.example.fooddelivery.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.User
import com.google.gson.Gson


class MenuFragment : Fragment() {
    lateinit var backBtn: ImageButton
    lateinit var cartBtn: FrameLayout
    lateinit var ordersBtn: FrameLayout
    lateinit var personalInfoBtn: FrameLayout
    lateinit var paymentBtn: FrameLayout
    lateinit var addressBtn: FrameLayout
    lateinit var logoutBtn: FrameLayout

    lateinit var name: TextView
    lateinit var bio: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        cartBtn = view.findViewById(R.id.cartBtn)
        cartBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        ordersBtn = view.findViewById(R.id.ordersBtn)
        ordersBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrdersFragment())
                .addToBackStack(null)
                .commit()
        }

        personalInfoBtn = view.findViewById(R.id.personalInfoBtn)
        personalInfoBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PersonalInfoFragment())
                .addToBackStack(null)
                .commit()
        }

        addressBtn = view.findViewById(R.id.addressBtn)
        addressBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddressFragment())
                .addToBackStack(null)
                .commit()
        }

        logoutBtn = view.findViewById(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            var prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit() {
                putBoolean("loggedIn", false)
            }
            prefs = requireContext().getSharedPreferences("app_prefs", MODE_PRIVATE)
            prefs.edit() {
                putBoolean("onboarding", false)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

        paymentBtn = view.findViewById(R.id.paymentBtn)
        paymentBtn.setOnClickListener {
            val fragment = PaymentFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("source", "menu")
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        name = view.findViewById(R.id.name)
        bio = view.findViewById(R.id.bio)
        loadUser()

        return view
    }

    fun loadUser() {
        val sharedPreferences = requireContext().getSharedPreferences("user", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(
            "user", gson.toJson(
                User(
                    name = "Vishal Khadok",
                    email = "hello@halallab.co",
                    phone = "408-841-0926",
                    bio = "I love fast food"
                )
            )
        )

        var user: User = gson.fromJson(json, User::class.java)
        name.text = user.name
        bio.text = user.bio
    }
}
