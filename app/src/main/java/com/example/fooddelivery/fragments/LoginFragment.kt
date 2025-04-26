package com.example.fooddelivery.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R

class LoginFragment : Fragment() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var forgotPasswordBtn: TextView
    private lateinit var signupBtn: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frament_log_in, container, false)

        editEmail = view.findViewById(R.id.editTextEmail)
        editPassword = view.findViewById(R.id.editTextPassword)
        loginBtn = view.findViewById(R.id.loginBtn)
        forgotPasswordBtn = view.findViewById(R.id.forgotPasswordBtn)
        signupBtn = view.findViewById(R.id.signupBtn)

        loginBtn.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Enter email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit() {
                putBoolean("loggedIn", true)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }


        return view
    }
}