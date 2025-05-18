package com.example.fooddelivery.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.models.User
import com.google.gson.Gson


class EditProfileFragment : Fragment() {
    lateinit var backBtn: ImageButton

    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPhone: EditText
    lateinit var editBio: EditText

    lateinit var saveBtn: Button
    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        editName = view.findViewById(R.id.editTextName)
        editEmail = view.findViewById(R.id.editTextEmail)
        editPhone = view.findViewById(R.id.editTextPhone)
        editBio = view.findViewById(R.id.editTextBio)
        loadUser()

        saveBtn = view.findViewById(R.id.saveBtn)
        saveBtn.setOnClickListener {
            user.name = editName.text.toString()
            user.email = editEmail.text.toString()
            user.phone = editPhone.text.toString()
            user.bio = editBio.text.toString()

            val sharedPreferences = requireContext().getSharedPreferences("user", MODE_PRIVATE)
            val gson = Gson()
            sharedPreferences.edit {
                putString("user", gson.toJson(user))
            }

            parentFragmentManager.popBackStack()
        }

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
        user = gson.fromJson(json, User::class.java)

        editName.setText(user.name)
        editEmail.setText(user.email)
        editPhone.setText(user.phone)
        editBio.setText(user.bio)
    }
}