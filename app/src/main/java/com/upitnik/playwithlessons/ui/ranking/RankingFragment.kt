package com.upitnik.playwithlessons.ui.ranking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.gone
import com.upitnik.playwithlessons.core.extensions.visible
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.remote.ranking.RankingDataSource
import com.upitnik.playwithlessons.databinding.FragmentRankingBinding
import com.upitnik.playwithlessons.domain.ranking.RankingRepoImpl
import com.upitnik.playwithlessons.presentation.ranking.RankingViewModel
import com.upitnik.playwithlessons.presentation.ranking.RankingViewModelFactory


class RankingFragment : Fragment(R.layout.fragment_ranking), OnUserActionListener {
    private val viewModel by viewModels<RankingViewModel> {
        RankingViewModelFactory(
            RankingRepoImpl(
                RankingDataSource()
            )
        )
    }

    private lateinit var binding: FragmentRankingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRankingBinding.bind(view)
        getUsers()

    }

    private fun getUsers() {
        viewModel.getUsers().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbRanking.visible()
                    binding.rvRanking.gone()
                }
                is Result.Sucess -> {
                    binding.pbRanking.gone()
                    binding.rvRanking.visible()
                    if (result.data.isNotEmpty()) {
                        binding.rvRanking.adapter =
                            RankingAdapter(result.data, this@RankingFragment)
                    }
                }
                is Result.Failure -> {
                    binding.pbRanking.visible()
                    binding.rvRanking.gone()
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onUserClick(user: UserItem) {
        val bundle = Bundle()
        bundle.putSerializable(
            "User",
            UserItem(
                user.id,
                user.uid,
                user.email,
                user.experience,
                user.image,
                user.nickname,
                user.question,
                user.score
            )
        )
        findNavController().navigate(R.id.action_Ranking_to_Profile, bundle)
    }


}