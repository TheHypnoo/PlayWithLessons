package com.upitnik.playwithlessons.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.data.model.auth.Achievement
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.databinding.FragmentProfileBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.tvSignOut.setOnClickListener {
            viewModel.signOut().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        /* binding.progressBar.visibility = View.VISIBLE
                         binding.btnSignIn.isEnabled = false*/
                    }
                    is Result.Sucess -> {
                        //binding.progressBar.visibility = View.GONE
                        findNavController().navigate(R.id.action_profile_to_loginFragment)
                        Toast.makeText(
                            requireContext(),
                            "Has salido bro!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Failure -> {
                        /* binding.btnSignIn.isEnabled = true
                         binding.progressBar.visibility = View.GONE*/
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
        val logro1 = Achievement(
            "Por ser guapo",
            "https://m.media-amazon.com/images/I/71jod9LB42L._SS500_.jpg",
            0
        )
        val listLevel = listOf(logro1, logro1, logro1, logro1, logro1, logro1, logro1, logro1)
        binding.rvAchievement.layoutManager = GridLayoutManager(this@ProfileFragment.context, 5)
        val adapter = AchievementsAdapter(listLevel)
        binding.rvAchievement.adapter = adapter
    }
}