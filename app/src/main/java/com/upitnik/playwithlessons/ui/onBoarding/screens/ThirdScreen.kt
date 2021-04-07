package com.upitnik.playwithlessons.ui.onBoarding.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment(R.layout.fragment_third_screen) {
    private lateinit var binding: FragmentThirdScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding = FragmentThirdScreenBinding.bind(view)

        binding.tvFinish.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
            //onBoardingFinished()
            //Lo dejo así para tema pruebas
        }
        binding.tvPrevious.setOnClickListener {
            viewPager?.currentItem = 1
        }

    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}