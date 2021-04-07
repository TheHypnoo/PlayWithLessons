package com.upitnik.playwithlessons.ui.onBoarding.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentSecondScreenBinding


class SecondScreen : Fragment(R.layout.fragment_second_screen) {
    private lateinit var binding: FragmentSecondScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding = FragmentSecondScreenBinding.bind(view)

        binding.tvNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
        binding.tvPrevious.setOnClickListener {
            viewPager?.currentItem = 0
        }
    }
}