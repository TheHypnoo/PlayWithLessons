package com.upitnik.playwithlessons.ui.ranking

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.gone
import com.upitnik.playwithlessons.core.extensions.invisible
import com.upitnik.playwithlessons.core.extensions.visible
import com.upitnik.playwithlessons.data.model.authentication.User
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
                    binding.rvRanking.invisible()
                }
                is Result.Sucess -> {
                    binding.pbRanking.gone()
                    binding.rvRanking.visible()
                    if (result.data.isNotEmpty()) {
                        binding.rvRanking.adapter =
                            RankingAdapter(
                                result.data.sortedByDescending { it.score },
                                this@RankingFragment
                            )
                    }
                }
                is Result.Failure -> {
                    binding.pbRanking.visible()
                    binding.rvRanking.invisible()
                    dialogError(result.exception.toString())
                    /*Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            }
        })
    }

    override fun onUserClick(user: User) {
        val bundle = Bundle()
        bundle.putSerializable(
            "User",
            User(
                user.id,
                user.uid,
                user.email,
                user.experience,
                user.image,
                user.nickname,
                user.score
            )
        )
        findNavController().navigate(R.id.action_Ranking_to_Profile, bundle)
    }


    private fun dialogError(message: String) {
        val view = View.inflate(binding.root.context, R.layout.dialog_error, null)

        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnConfirm = view.findViewById<Button>(R.id.btn_leave)
        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
        val textMessage = view.findViewById<TextView>(R.id.textMessage)
        textMessage.text = message
    }


}