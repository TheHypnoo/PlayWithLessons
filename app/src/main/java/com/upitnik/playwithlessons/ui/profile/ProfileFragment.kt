package com.upitnik.playwithlessons.ui.profile

import android.content.Context
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
import com.upitnik.playwithlessons.core.extensions.gone
import com.upitnik.playwithlessons.core.extensions.invisible
import com.upitnik.playwithlessons.core.extensions.visible
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.remote.authentication.AuthDataSource
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
    private var user: User? = null
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        signOutClick()
        Glide.with(binding.root.context).load(user!!.image)
            .into(binding.civImageUser)
        binding.tvUsername.text = user!!.nickname
        binding.tvLevelUser.text = checkExperience(user!!.experience)
        binding.tvScoreUser.text = user!!.score.toString()
        scoreUser = user!!.score
        getAchievements()
    }

    private fun signOutClick() {
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
                            "Has salido de la sesión",
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

    private fun getAchievements() {
        userViewModel.getAchievements().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbAchievements.visible()
                    binding.rvAchievement.invisible()
                }
                is Result.Sucess -> {
                    binding.pbAchievements.gone()
                    binding.rvAchievement.visible()
                    binding.rvAchievement.layoutManager =
                        GridLayoutManager(this@ProfileFragment.context, 5)
                    val adapter = AchievementsAdapter(result.data, scoreUser)
                    binding.rvAchievement.adapter = adapter
                }
                is Result.Failure -> {
                    binding.pbAchievements.visible()
                    binding.rvAchievement.invisible()
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun checkExperience(experiencie: Int): String {
        return when (experiencie.toString().length) {
            //5
            1 -> "Nivel 0"
            //25
            2 -> "Nivel 0"
            //100
            3 -> "Nivel ${experiencie.toString().substring(0, 1)}"
            //1001
            4 -> "Nivel ${experiencie.toString().substring(0, 2)}"
            //10001
            5 -> "Nivel ${experiencie.toString().substring(0, 3)}"
            else -> "Nivel 0"
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getSerializable("User") as User?
    }

}