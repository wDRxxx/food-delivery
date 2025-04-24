package com.example.fooddelivery.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddelivery.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnboardingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.onboarding_fragment, container, false)

        val fragmentList = arrayListOf<Fragment>(
            OnboardingScreen1(),
            OnboardingScreen2(),
            OnboardingScreen3(),
            OnboardingScreen4(),
        )

        val adapter = Adapter(
            fragmentList, requireActivity().supportFragmentManager, lifecycle
        )
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = adapter

        val indicator = view.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(viewPager)

        val nextBtn = view.findViewById<Button>(R.id.nextBtn)
        val skipBtn = view.findViewById<TextView>(R.id.skipBtn)

        nextBtn.setOnClickListener {
            if (viewPager.currentItem < 3) {
                viewPager.currentItem++
            }

            if (viewPager.currentItem == 3) {
                nextBtn.text = "GET STARTED"
            }
        }

        return view
    }
}