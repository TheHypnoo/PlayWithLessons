package com.upitnik.playwithlessons.ui.onBoarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentViewPagerBinding
import com.upitnik.playwithlessons.ui.onBoarding.screens.FirstScreen
import com.upitnik.playwithlessons.ui.onBoarding.screens.SecondScreen
import com.upitnik.playwithlessons.ui.onBoarding.screens.ThirdScreen


class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private lateinit var binding: FragmentViewPagerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPagerBinding.bind(view)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
    }
}