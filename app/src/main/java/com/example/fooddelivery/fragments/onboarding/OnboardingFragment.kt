package com.example.fooddelivery.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        )

        val adapter = Adapter(
            fragmentList, requireActivity().supportFragmentManager, lifecycle
        )
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = adapter

        val indicator = view.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(viewPager)

        return view
    }
}