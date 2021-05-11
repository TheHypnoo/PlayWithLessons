package com.upitnik.playwithlessons.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.data.remote.profile.ProfileDataSource
import com.upitnik.playwithlessons.databinding.FragmentProfileBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.domain.profile.ProfileRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory
import com.upitnik.playwithlessons.presentation.profile.ProfileViewModel
import com.upitnik.playwithlessons.presentation.profile.ProfileViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    private val userViewModel by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(
            ProfileRepoImpl(
                ProfileDataSource()
            )
        )
    }
    var scoreUser: Int = 0
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        getUser()
        getAchievements()
        binding.tvSignOut.setOnClickListener {
            authViewModel.signOut().observe(viewLifecycleOwner, Observer { result ->
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
    }

    private fun getUser() {
        userViewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbProfile.visibility = View.VISIBLE
                }
                is Result.Sucess -> {
                    binding.pbProfile.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Welcome ${result.data.nickname}",
                        Toast.LENGTH_SHORT
                    ).show()

                    Glide.with(binding.root.context).load(result.data.image)
                        .into(binding.civImageUser)
                    binding.tvUsername.text = result.data.nickname
                    binding.tvLevelUser.text = "Nivel ${result.data.experience}"
                    binding.tvScoreUser.text = result.data.score.toString()
                    scoreUser = result.data.score
                }
                is Result.Failure -> {
                    binding.pbProfile.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getAchievements() {
        userViewModel.getAchievements().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbAchievements.visibility = View.VISIBLE
                    binding.rvAchievement.visibility = View.GONE
                }
                is Result.Sucess -> {
                    binding.pbAchievements.visibility = View.GONE
                    binding.rvAchievement.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Welcome ${result.data}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.rvAchievement.layoutManager =
                        GridLayoutManager(this@ProfileFragment.context, 5)
                    val adapter = AchievementsAdapter(result.data, scoreUser)
                    binding.rvAchievement.adapter = adapter
                }
                is Result.Failure -> {
                    binding.pbAchievements.visibility = View.VISIBLE
                    binding.rvAchievement.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

}