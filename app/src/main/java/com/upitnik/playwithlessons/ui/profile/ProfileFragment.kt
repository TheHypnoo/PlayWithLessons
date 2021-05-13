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
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.data.remote.profile.ProfileDataSource
import com.upitnik.playwithlessons.databinding.FragmentProfileBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.domain.profile.ProfileRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory
import com.upitnik.playwithlessons.presentation.profile.ProfileViewModel
import com.upitnik.playwithlessons.presentation.profile.ProfileViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private var user: UserItem? = null
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        signOutClick()
        visibilityStatsProfile(false)
        if (user == null) {
            getUser()
        } else {
            Glide.with(binding.root.context).load(user!!.image)
                .into(binding.civImageUser)
            binding.tvUsername.text = user!!.nickname
            binding.tvLevelUser.text = checkExperience(user!!.experience)
            binding.tvScoreUser.text = user!!.score.toString()
            scoreUser = user!!.score
            getAchievements()
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            visibilityStatsProfile(true)
        }
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
                    visibilityCardViewProfile(false)
                }
                is Result.Sucess -> {
                    getAchievements()
                    binding.pbProfile.visibility = View.GONE
                    visibilityCardViewProfile(true)
                    /*Toast.makeText(
                        requireContext(),
                        "Welcome ${result.data.nickname}",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    Glide.with(binding.root.context).load(result.data.image)
                        .into(binding.civImageUser)
                    binding.tvUsername.text = result.data.nickname
                    binding.tvLevelUser.text = checkExperience(result.data.experience)
                    binding.tvScoreUser.text = result.data.score.toString()
                    scoreUser = result.data.score
                }
                is Result.Failure -> {
                    binding.pbProfile.visibility = View.VISIBLE
                    visibilityCardViewProfile(false)
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
                    binding.rvAchievement.visibility = View.INVISIBLE
                }
                is Result.Sucess -> {
                    binding.pbAchievements.visibility = View.GONE
                    binding.rvAchievement.visibility = View.VISIBLE
                    /* Toast.makeText(
                         requireContext(),
                         "Welcome ${result.data}",
                         Toast.LENGTH_SHORT
                     ).show()*/
                    binding.rvAchievement.layoutManager =
                        GridLayoutManager(this@ProfileFragment.context, 5)
                    val adapter = AchievementsAdapter(result.data, scoreUser)
                    binding.rvAchievement.adapter = adapter
                }
                is Result.Failure -> {
                    binding.pbAchievements.visibility = View.VISIBLE
                    binding.rvAchievement.visibility = View.INVISIBLE
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

    private fun visibilityCardViewProfile(visible: Boolean) {
        if (visible) {
            binding.civImageUser.visibility = View.VISIBLE
            binding.tvUsername.visibility = View.VISIBLE
            binding.tvLevelUser.visibility = View.VISIBLE
            binding.ivScore.visibility = View.VISIBLE
            binding.tvScoreUser.visibility = View.VISIBLE
        } else {
            binding.civImageUser.visibility = View.GONE
            binding.tvUsername.visibility = View.GONE
            binding.tvLevelUser.visibility = View.GONE
            binding.ivScore.visibility = View.GONE
            binding.tvScoreUser.visibility = View.GONE
        }
    }

    private fun visibilityStatsProfile(visible: Boolean) {
        if (visible) {
            binding.pbStats.visibility = View.GONE
            binding.acertadas.visibility = View.VISIBLE
            binding.spacer.visibility = View.VISIBLE
            binding.fallados.visibility = View.VISIBLE
        } else {
            binding.pbStats.visibility = View.VISIBLE
            binding.acertadas.visibility = View.INVISIBLE
            binding.spacer.visibility = View.INVISIBLE
            binding.fallados.visibility = View.INVISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getSerializable("User") as UserItem?
    }

}