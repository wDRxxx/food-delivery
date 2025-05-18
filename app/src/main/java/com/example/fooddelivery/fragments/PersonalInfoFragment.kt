package com.example.fooddelivery.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.User
import com.google.gson.Gson


class PersonalInfoFragment : Fragment() {
    lateinit var backBtn: ImageButton
    lateinit var editBtn: TextView

    lateinit var name1: TextView
    lateinit var name2: TextView
    lateinit var phone: TextView
    lateinit var email: TextView
    lateinit var bio: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_info, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        editBtn = view.findViewById(R.id.editBtn)
        editBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EditProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        name1 = view.findViewById(R.id.name)
        name2 = view.findViewById(R.id.name2)
        phone = view.findViewById(R.id.phone)
        email = view.findViewById(R.id.email)
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
        name1.text = user.name
        name2.text = user.name
        phone.text = user.phone
        email.text = user.email
        bio.text = user.bio
    }
}
