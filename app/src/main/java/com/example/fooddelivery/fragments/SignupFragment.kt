package com.example.fooddelivery.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R

class SignupFragment : Fragment() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editRePassword: EditText
    private lateinit var editName: EditText
    private lateinit var signupBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        editEmail = view.findViewById(R.id.editTextEmail)
        editName = view.findViewById(R.id.editTextName)
        editPassword = view.findViewById(R.id.editTextPassword)
        editRePassword = view.findViewById(R.id.editTextRePassword)
        signupBtn = view.findViewById(R.id.signupBtn)

        signupBtn.setOnClickListener {
            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val rePassword = editRePassword.text.toString().trim()

            Log.println(Log.INFO, "email", email)

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(requireContext(), "Enter email and password", Toast.LENGTH_SHORT)
                        .show()
                }

                password != rePassword -> {
                    Toast.makeText(
                        requireContext(),
                        "Passwords should be the same",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                name.isEmpty() -> {
                    Toast.makeText(requireContext(), "Enter name", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val prefs =
                        requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putBoolean("loggedIn", true).apply()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                }
            }
        }


        return view
    }
}