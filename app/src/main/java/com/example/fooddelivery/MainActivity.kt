package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelivery.fragments.HomeFragment
import com.example.fooddelivery.fragments.onboarding.OnboardingFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val onboardingCompleted = prefs.getBoolean("onboarding", false)
        if (savedInstanceState == null) {
            if (!onboardingCompleted) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, OnboardingFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
        }
    }
}