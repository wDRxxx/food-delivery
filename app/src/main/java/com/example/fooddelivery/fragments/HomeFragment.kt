package com.example.fooddelivery.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import com.example.fooddelivery.R

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.button_menu).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MenuFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.button_details).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
