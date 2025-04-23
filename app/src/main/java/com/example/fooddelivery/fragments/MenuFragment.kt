package com.example.fooddelivery.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R

class MenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }
}